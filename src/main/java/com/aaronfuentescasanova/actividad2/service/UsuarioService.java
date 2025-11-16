package com.aaronfuentescasanova.actividad2.service;

import com.aaronfuentescasanova.actividad2.dto.CambiarPasswordRequest;
import com.aaronfuentescasanova.actividad2.dto.UpdateUsuarioRequest;
import com.aaronfuentescasanova.actividad2.dto.UsuarioDTO;
import com.aaronfuentescasanova.actividad2.entity.Usuario;
import com.aaronfuentescasanova.actividad2.exception.DuplicateEmailException;
import com.aaronfuentescasanova.actividad2.exception.ResourceNotFoundException;
import com.aaronfuentescasanova.actividad2.exception.UnauthorizedException;
import com.aaronfuentescasanova.actividad2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO getPerfil() {
        Usuario usuario = getCurrentUser();
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Long id, UpdateUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!usuario.getEmail().equals(request.getEmail()) && 
            usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("El email ya está en uso");
        }

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());

        Usuario updated = usuarioRepository.save(usuario);
        return modelMapper.map(updated, UsuarioDTO.class);
    }

    @Transactional
    public void cambiarPassword(CambiarPasswordRequest request) {
        Usuario usuario = getCurrentUser();

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword())) {
            throw new UnauthorizedException("La contraseña actual es incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
    }

    public Usuario getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}