package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.repository.UsuarioRepository;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usuNombre) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuNombreAndUsuEstado(usuNombre, true);
        Usuario usuario = usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro " +
                        "Usuario con nombre : " + usuNombre));

        return new org.springframework.security
                .core.userdetails.User(usuario.getUsuNombre(), usuario.getUsuPassword(),
                usuario.isUsuEstado(), true, true,
                true, getAuthorities("USUARIO"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
