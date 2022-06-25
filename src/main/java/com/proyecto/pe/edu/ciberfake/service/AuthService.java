package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.proyecto.pe.edu.ciberfake.dto.*;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.model.EmailNotificacion;
import com.proyecto.pe.edu.ciberfake.model.TokenVerificacion;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.repository.TokenVerificacionRepository;
import com.proyecto.pe.edu.ciberfake.repository.UsuarioRepository;
import com.proyecto.pe.edu.ciberfake.security.JwtProvider;

import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final TokenVerificacionRepository tokenVerificacionRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final TokenRefreshService tokenRefreshService;

    public RegistroResponse registrarse(RegistroRequest registroRequest){
        Usuario usuario =  new Usuario();
        usuario.setUsuNombre(registroRequest.getUsuario().toUpperCase());
        usuario.setUsuEmail(registroRequest.getEmail().toUpperCase());
        usuario.setUsuPassword(passwordEncoder.encode(registroRequest.getPassword()));
        usuario.setUsuPerNombre(registroRequest.getNombres().toUpperCase());
        usuario.setUsuPerApellido(registroRequest.getApellidos().toUpperCase());
        usuario.setUsuPerCodigo(registroRequest.getCodigo().toUpperCase());
        usuario.setUsuEstado(false);

        usuarioRepository.save(usuario);

        String token = generarTokenVerificacion(usuario);
        mailService.enviarMail(new EmailNotificacion("Porfavor, Active su cuenta",
                usuario.getUsuEmail(),
                "Muchas gracias por registrarte a Cibernet, " +
                        "porfavor ingrese al siguiente enlace para activar tu cuenta : " +
                        "http://localhost:8080/api/auth/verificacionCuenta/" + token));
        return new RegistroResponse("Registro de usuario exitoso",token);
    }

    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioActual() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByUsuNombreAndUsuEstado(principal.getUsername(), true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con nombre "+ principal.getUsername() +" no se encontro."));
    }

    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorUsuario(String usuNombre) {
        Usuario usuario = usuarioRepository.findByUsuNombreAndUsuEstado(usuNombre, true)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro Usuario con nombre: "+usuNombre));
        return usuario;
    }

    private void buscarUsuarioActivar(TokenVerificacion tokenVerificacion) {
        String usuNombre = tokenVerificacion.getUsuario().getUsuNombre();
        Usuario usuario = usuarioRepository.findByUsuNombre(usuNombre).orElseThrow(() -> new CiberFakeException("Usuario con nombre "+usuNombre+" no se encontro."));
        usuario.setUsuEstado(true);
        usuarioRepository.save(usuario);
    }

    private String generarTokenVerificacion(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        TokenVerificacion tokenVerificacion = new TokenVerificacion();
        tokenVerificacion.setTokvToken(token);
        tokenVerificacion.setUsuario(usuario);
        tokenVerificacion.setTokvFecExp(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()));

        tokenVerificacionRepository.save(tokenVerificacion);
        return token;
    }

    public void verificarCuenta(String token) {
        Optional<TokenVerificacion> tokenVerificacion = tokenVerificacionRepository.findByTokvToken(token);
        buscarUsuarioActivar(tokenVerificacion.orElseThrow(() -> new CiberFakeException("Token Invalido")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsuario(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generarToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(tokenRefreshService.generarTokenRefresh().getTokrToken())
                .expiraEn(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .usuario(loginRequest.getUsuario())
                .build();
    }

    public AuthenticationResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        tokenRefreshService.validarTokenRefresh(tokenRefreshRequest.getTokenRefresh());
        String token = jwtProvider.generarTokenConUsuNombre(tokenRefreshRequest.getUsuario());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(tokenRefreshRequest.getTokenRefresh())
                .expiraEn(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .usuario(tokenRefreshRequest.getUsuario())
                .build();
    }

    public boolean estaLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public String actualizarCuenta(RegistroRequest registroRequest) {
        Usuario usuario =  new Usuario();
        Usuario usuarioDB = usuarioRepository.findById(registroRequest.getId()).orElse(null);

        usuario.setUsuId(registroRequest.getId());
        usuario.setUsuNombre(registroRequest.getUsuario().toUpperCase());
        usuario.setUsuEmail(registroRequest.getEmail().toUpperCase());
        if(registroRequest.getPassword() == null) {
            usuario.setUsuPassword(passwordEncoder.encode(registroRequest.getPassword()));
        }else{
            usuario.setUsuPassword(usuarioDB.getUsuPassword());
        }
        usuario.setUsuPerNombre(registroRequest.getNombres().toUpperCase());
        usuario.setUsuPerApellido(registroRequest.getApellidos().toUpperCase());
        usuario.setUsuPerCodigo(registroRequest.getCodigo().toUpperCase());
        usuario.setUsuEstado(registroRequest.getEstado());

        usuarioRepository.save(usuario);
        return "Actualizacion de usuario exitoso";
    }
}
