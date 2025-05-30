package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByCiudad(String ciudad);
    List<Direccion> findByCodigoPostal(String codigoPostal);
    List<Direccion> findByCiudadAndCodigoPostal(String ciudad, String codigoPostal);
    List<Direccion> findByCalleContainingIgnoreCase(String calle);
}