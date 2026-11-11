package com.example.almacen.Repository;

import com.example.almacen.modelo.Usuario;
import com.example.almacen.modelo.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Usuario> findAllByOrderByNombreAsc();

    boolean existsByRolAndEstadoTrueAndPasswordHashStartingWith(RolUsuario rol, String prefijo);
}
