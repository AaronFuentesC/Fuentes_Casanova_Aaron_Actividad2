package com.aaronfuentescasanova.actividad2.dto;

import com.aaronfuentescasanova.actividad2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private Role role;
    private String email;
}
