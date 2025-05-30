//package com.example.demoGestoriaPizzeria.Repository;
//
//import com.example.demoGestoriaPizzeria.Model.Bebida;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface BebidaRepository extends JpaRepository<Bebida, Long> {
//
//}

package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Enums.TamanoBebida;
import com.example.demoGestoriaPizzeria.Model.Bebida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    List<Bebida> findByTamano(TamanoBebida tamano);
    List<Bebida> findByDisponibleTrue();
    List<Bebida> findByNombreContainingIgnoreCase(String nombre);
    Optional<Bebida> findByNombre(String nombre);

    // Método adicional útil
    Optional<Bebida> findByNombreAndTamano(String nombre, TamanoBebida tamano);
    List<Bebida> findByDisponibleTrueAndCantidadGreaterThan(Integer cantidad);
}