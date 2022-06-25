package com.proyecto.pe.edu.ciberfake.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.pe.edu.ciberfake.dto.*;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.service.ActivacionContentBuilder;
import com.proyecto.pe.edu.ciberfake.service.AuthService;
import com.proyecto.pe.edu.ciberfake.service.TokenRefreshService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ActivacionContentBuilder activacionContentBuilder;
    private final TokenRefreshService tokenRefreshService;

    @PostMapping("/registrarse")
    public ResponseEntity<RegistroResponse> registrarse(@RequestBody RegistroRequest registroRequest){
        return new ResponseEntity<RegistroResponse>(authService.registrarse(registroRequest), HttpStatus.OK);
    }

    @GetMapping("/verificacionCuenta/{token}")
    public ResponseEntity<String> verificarCuenta(@PathVariable String token){
        authService.verificarCuenta(token);
        String contenido = activacionContentBuilder.build();
        return new ResponseEntity<>(contenido, HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return authService.refreshToken(tokenRefreshRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequest tokenRefreshRequest) {
        tokenRefreshService.borrarTokenRefresh(tokenRefreshRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token eliminado exitosamente!!");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarCuenta(@RequestBody RegistroRequest registroRequest){
        return ResponseEntity.status(OK).body(authService.actualizarCuenta(registroRequest));
    }

    @GetMapping("/por-usuario/{usuNombre}")
    public ResponseEntity<Usuario> obtenerUsuarioPorUsuario(@PathVariable String usuNombre) {
        return status(HttpStatus.OK).body(authService.obtenerUsuarioPorUsuario(usuNombre));
    }
}
