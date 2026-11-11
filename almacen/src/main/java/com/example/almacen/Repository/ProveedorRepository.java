package com.example.almacen.Repository;

import com.example.almacen.modelo.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    List<Proveedor> findAllByOrderByRazonSocialAsc();

    Optional<Proveedor> findByRuc(String ruc);
}
