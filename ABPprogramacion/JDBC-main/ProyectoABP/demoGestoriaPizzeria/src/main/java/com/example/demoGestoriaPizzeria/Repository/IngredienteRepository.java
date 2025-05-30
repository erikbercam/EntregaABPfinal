//package com.example.demoGestoriaPizzeria.Repository;
//
//import com.example.demoGestoriaPizzeria.Model.Ingrediente;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

//}
package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    Optional<Ingrediente> findByNombre(String nombre);
    List<Ingrediente> findByEsVeganoTrue();
    List<Ingrediente> findByEsIngredienteSinGlutenTrue();
    List<Ingrediente> findByCantidadGreaterThan(int cantidad);
    List<Ingrediente> findByCantidadLessThanEqual(int cantidad);
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT i FROM Ingrediente i WHERE i.cantidad = 0")
    List<Ingrediente> findIngredientesAgotados();
}