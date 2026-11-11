package com.example.almacen.Repository;

import com.example.almacen.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByOrderByNombreAsc();

    List<Categoria> findByEstadoTrueOrderByNombreAsc();

    Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
