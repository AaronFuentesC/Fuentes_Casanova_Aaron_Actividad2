package com.aaronfuentescasanova.actividad2.config;

import com.aaronfuentescasanova.actividad2.entity.Aula;
import com.aaronfuentescasanova.actividad2.entity.TramoHorario;
import com.aaronfuentescasanova.actividad2.entity.Usuario;
import com.aaronfuentescasanova.actividad2.enums.DiaSemana;
import com.aaronfuentescasanova.actividad2.enums.Role;
import com.aaronfuentescasanova.actividad2.enums.TipoTramo;
import com.aaronfuentescasanova.actividad2.repository.AulaRepository;
import com.aaronfuentescasanova.actividad2.repository.TramoHorarioRepository;
import com.aaronfuentescasanova.actividad2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final AulaRepository aulaRepository;
    private final TramoHorarioRepository tramoHorarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Crear usuarios de prueba
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin Principal");
            admin.setEmail("admin@educativo.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            usuarioRepository.save(admin);

            Usuario profesor = new Usuario();
            profesor.setNombre("Profesor García");
            profesor.setEmail("profesor@educativo.com");
            profesor.setPassword(passwordEncoder.encode("profesor123"));
            profesor.setRole(Role.ROLE_PROFESOR);
            usuarioRepository.save(profesor);

            System.out.println("Usuarios creados:");
            System.out.println("  - Admin: admin@educativo.com / admin123");
            System.out.println("  - Profesor: profesor@educativo.com / profesor123");
        }

        // Crear aulas de prueba
        if (aulaRepository.count() == 0) {
            Aula aula1 = new Aula();
            aula1.setNombre("Aula 101");
            aula1.setCapacidad(30);
            aula1.setEsAulaDeOrdenadores(false);
            aula1.setNumeroOrdenadores(0);
            aulaRepository.save(aula1);

            Aula aula2 = new Aula();
            aula2.setNombre("Laboratorio Informática 1");
            aula2.setCapacidad(25);
            aula2.setEsAulaDeOrdenadores(true);
            aula2.setNumeroOrdenadores(25);
            aulaRepository.save(aula2);

            Aula aula3 = new Aula();
            aula3.setNombre("Salón de Actos");
            aula3.setCapacidad(100);
            aula3.setEsAulaDeOrdenadores(false);
            aula3.setNumeroOrdenadores(0);
            aulaRepository.save(aula3);

            System.out.println("Aulas creadas: 3");
        }

        // Crear tramos horarios de prueba
        if (tramoHorarioRepository.count() == 0) {
            // Lunes
            createTramo(DiaSemana.LUNES, 1, LocalTime.of(8, 0), LocalTime.of(9, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.LUNES, 2, LocalTime.of(9, 0), LocalTime.of(10, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.LUNES, 3, LocalTime.of(10, 0), LocalTime.of(11, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.LUNES, 4, LocalTime.of(11, 0), LocalTime.of(11, 30), TipoTramo.RECREO);
            createTramo(DiaSemana.LUNES, 5, LocalTime.of(11, 30), LocalTime.of(12, 30), TipoTramo.LECTIVA);
            createTramo(DiaSemana.LUNES, 6, LocalTime.of(12, 30), LocalTime.of(13, 30), TipoTramo.LECTIVA);

            // Martes
            createTramo(DiaSemana.MARTES, 1, LocalTime.of(8, 0), LocalTime.of(9, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.MARTES, 2, LocalTime.of(9, 0), LocalTime.of(10, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.MARTES, 3, LocalTime.of(10, 0), LocalTime.of(11, 0), TipoTramo.LECTIVA);
            createTramo(DiaSemana.MARTES, 4, LocalTime.of(11, 0), LocalTime.of(11, 30), TipoTramo.RECREO);

            System.out.println("Tramos horarios creados: 10");
        }
    }

    private void createTramo(DiaSemana dia, int sesion, LocalTime inicio, LocalTime fin, TipoTramo tipo) {
        TramoHorario tramo = new TramoHorario();
        tramo.setDiaSemana(dia);
        tramo.setSesionDia(sesion);
        tramo.setHoraInicio(inicio);
        tramo.setHoraFin(fin);
        tramo.setTipo(tipo);
        tramoHorarioRepository.save(tramo);
    }
}