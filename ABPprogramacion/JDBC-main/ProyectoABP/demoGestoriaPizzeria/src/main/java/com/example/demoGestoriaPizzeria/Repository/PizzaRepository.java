//package com.example.demoGestoriaPizzeria.Repository;
//
//import com.example.demoGestoriaPizzeria.Enums.enumMassa;
//import com.example.demoGestoriaPizzeria.Enums.enumPizza;
//import com.example.demoGestoriaPizzeria.Model.Pizza;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface PizzaRepository extends JpaRepository<Pizza, Long> {
//
//}

package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Pizza;
import com.example.demoGestoriaPizzeria.Enums.enumPizza;
import com.example.demoGestoriaPizzeria.Enums.enumMassa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByTipo(enumPizza tipo);

    List<Pizza> findByEsSinGlutenTrue();

    List<Pizza> findByTipoMasa(enumMassa tipoMasa);

    List<Pizza> findByTipoAndTipoMasa(enumPizza tipo, enumMassa tipoMasa);

    List<Pizza> findByDescripcionContainingIgnoreCase(String descripcion);

    List<Pizza> findByNombreContainingIgnoreCase(String nombre); // 💡 Añadir este método si se llama desde el service

    List<Pizza> findByPrecioLessThan(Double precio); // 💡 Añadir este método también si se usa desde el service

    @Query("SELECT p FROM Pizza p JOIN p.ingredientes i WHERE i.nombre = :nombreIngrediente")
    List<Pizza> findPizzasConIngrediente(String nombreIngrediente);

    @Query("SELECT p FROM Pizza p WHERE p.esSinGluten = true AND " +
            "EXISTS (SELECT i FROM p.ingredientes i WHERE i.esIngredienteSinGluten = true)")
    List<Pizza> findPizzasCompletamenteSinGluten();
}