//package com.example.demoGestoriaPizzeria.Services;
//
//import com.example.demoGestoriaPizzeria.Enums.enumMassa;
//import com.example.demoGestoriaPizzeria.Enums.enumPizza;
//import com.example.demoGestoriaPizzeria.Model.Pizza;
//import com.example.demoGestoriaPizzeria.Repository.PizzaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PizzaService {
//
//    @Autowired
//    private PizzaRepository pizzaRepository;
//
//    public List<Pizza> obtenerTodasLasPizzas() {
//        return pizzaRepository.findAll();
//    }
//
//    public Optional<Pizza> obtenerPizzaPorId(Long id) {
//        return pizzaRepository.findById(id);
//    }
//
//    public List<Pizza> obtenerPizzasDisponibles() {
//        return pizzaRepository.findByDisponibleTrue();
//    }
//
//    public List<Pizza> buscarPizzasPorTipo(enumPizza tipo) {
//        return pizzaRepository.findByTipo(tipo);
//    }
//
//    public List<Pizza> buscarPizzasPorTipoMasa(enumMassa tipoMasa) {
//        return pizzaRepository.findByTipoMasa(tipoMasa);
//    }
//
//    public Pizza crearPizza(Pizza pizza) {
//        return pizzaRepository.save(pizza);
//    }
//
//    public Pizza actualizarPizza(Long id, Pizza pizzaActualizada) {
//        return pizzaRepository.findById(id)
//                .map(pizzaExistente -> {
//                    pizzaExistente.setNombre(pizzaActualizada.getNombre());
//                    pizzaExistente.setDescripcion(pizzaActualizada.getDescripcion());
//                    pizzaExistente.setPrecio(pizzaActualizada.getPrecio());
//                    pizzaExistente.setTipo(pizzaActualizada.getTipo());
//                    pizzaExistente.setTipoMasa(pizzaActualizada.getTipoMasa());
//                    pizzaExistente.setDisponible(pizzaActualizada.isDisponible());
//                    pizzaExistente.setIngredientes(pizzaActualizada.getIngredientes());
//                    return pizzaRepository.save(pizzaExistente);
//                })
//                .orElseThrow(() -> new RuntimeException("Pizza no encontrada con id: " + id));
//    }
//
//    public void eliminarPizza(Long id) {
//        pizzaRepository.deleteById(id);
//    }
//
//    public List<Pizza> buscarPizzasPorNombre(String nombre) {
//        return pizzaRepository.findByNombreContainingIgnoreCase(nombre);
//    }
//
//    public List<Pizza> buscarPizzasPorPrecioMenorQue(Double precio) {
//        return pizzaRepository.findByPrecioBaseLessThan(precio);
//    }
//}
package com.example.demoGestoriaPizzeria.Services;

import com.example.demoGestoriaPizzeria.Enums.enumMassa;
import com.example.demoGestoriaPizzeria.Enums.enumPizza;
import com.example.demoGestoriaPizzeria.Model.Pizza;
import com.example.demoGestoriaPizzeria.Repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> obtenerTodasLasPizzas() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> obtenerPizzaPorId(Long id) {
        return pizzaRepository.findById(id);
    }

    public Pizza guardarPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza actualizarPizza(Long id, Pizza pizzaActualizada) {
        return pizzaRepository.findById(id)
                .map(pizzaExistente -> {
                    pizzaExistente.setNombre(pizzaActualizada.getNombre());
                    pizzaExistente.setPrecio(pizzaActualizada.getPrecio());
                    pizzaExistente.setCantidad(pizzaActualizada.getCantidad());
                    pizzaExistente.setDescripcion(pizzaActualizada.getDescripcion());
                    pizzaExistente.setTipo(pizzaActualizada.getTipo());
                    pizzaExistente.setTipoMasa(pizzaActualizada.getTipoMasa());
                    pizzaExistente.setEsSinGluten(pizzaActualizada.isEsSinGluten());
                    pizzaExistente.setIngredientes(pizzaActualizada.getIngredientes());
                    return pizzaRepository.save(pizzaExistente);
                })
                .orElseThrow(() -> new RuntimeException("Pizza no encontrada con id: " + id));
    }

    public void eliminarPizza(Long id) {
        pizzaRepository.deleteById(id);
    }

    public List<Pizza> buscarPizzasPorTipo(enumPizza tipo) {
        return pizzaRepository.findByTipo(tipo);
    }

    public List<Pizza> buscarPizzasPorTipoMasa(enumMassa tipoMasa) {
        return pizzaRepository.findByTipoMasa(tipoMasa);
    }

    public List<Pizza> buscarPizzasPorNombre(String nombre) {
        return pizzaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Pizza> buscarPizzasSinGluten() {
        return pizzaRepository.findByEsSinGlutenTrue();
    }

    public List<Pizza> buscarPizzasPorPrecioMenorQue(Double precio) {
        return pizzaRepository.findByPrecioLessThan(precio);
    }
}