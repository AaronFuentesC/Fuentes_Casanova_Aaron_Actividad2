package com.aaronfuentescasanova.actividad2.service;


import com.aaronfuentescasanova.actividad2.dto.AuthResponse;
import com.aaronfuentescasanova.actividad2.dto.LoginRequest;
import com.aaronfuentescasanova.actividad2.dto.RegisterRequest;
import com.aaronfuentescasanova.actividad2.entity.Usuario;
import com.aaronfuentescasanova.actividad2.exception.DuplicateEmailException;
import com.aaronfuentescasanova.actividad2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setRole(request.getRole());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(
                jwtToken,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRole()
        );
    }

    public AuthResponse login(LoginRequest request, AuthenticationManager authManager) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(
                jwtToken,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRole()
        );
    }
}
