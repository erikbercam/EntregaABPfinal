//package com.example.demoGestoriaPizzeria.Controller;

//import com.example.demoGestoriaPizzeria.Model.Pizza;
//import com.example.demoGestoriaPizzeria.Services.PizzaService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/pizzas")
//public class PizzaController {
//
//    @Autowired
//    private PizzaService pizzaService;
//
//    @GetMapping
//    public List<Pizza> obtenerTodasLasPizzas() {
//        return pizzaService.obtenerTodasLasPizzas();
//    }
//
//    @GetMapping("/{id}")
//    public Pizza obtenerPizzaPorId(@PathVariable Long id) {
//        return pizzaService.obtenerPizzaPorId(id);
//    }
//
//    @PostMapping
//    public Pizza crearPizza(@RequestBody Pizza pizza) {
//        return pizzaService.guardarPizza(pizza);
//    }
//
//    @PutMapping("/{id}")
//    public Pizza actualizarPizza(@PathVariable Long id, @RequestBody Pizza pizza) {
//        pizza.setId(id);
//        return pizzaService.guardarPizza(pizza);
//    }
//
//    @DeleteMapping("/{id}")
//    public void eliminarPizza(@PathVariable Long id) {
//        pizzaService.eliminarPizza(id);
//    }
//}

package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.DTO.PizzaDTO;
import com.example.demoGestoriaPizzeria.Enums.enumMassa;
import com.example.demoGestoriaPizzeria.Enums.enumPizza;
import com.example.demoGestoriaPizzeria.Model.Ingrediente;
import com.example.demoGestoriaPizzeria.Model.Pizza;
import com.example.demoGestoriaPizzeria.Repository.IngredienteRepository;
import com.example.demoGestoriaPizzeria.Repository.PizzaRepository;
import com.example.demoGestoriaPizzeria.Services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin(origins = "*")
public class PizzaController {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaService pizzaService;

    // Obtener todas las pizzas
    @GetMapping
    public ResponseEntity<List<Pizza>> obtenerTodasLasPizzas() {
        try {
            List<Pizza> pizzas = pizzaService.obtenerTodasLasPizzas();
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pizza por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> obtenerPizzaPorId(@PathVariable Long id) {
        try {
            Optional<Pizza> pizza = pizzaService.obtenerPizzaPorId(id);
            return pizza.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Pizza> crearPizza(@RequestBody PizzaDTO dto) {
        Pizza pizza = new Pizza();
        pizza.setNombre(dto.nombre);
        pizza.setDescripcion(dto.descripcion);
        pizza.setTipo(enumPizza.valueOf(dto.tipo));
        pizza.setTipoMasa(enumMassa.valueOf(dto.tipoMasa));
        pizza.setEsSinGluten(dto.esSinGluten);
        pizza.setPrecio(dto.precio);
        pizza.setCantidad(dto.cantidad);

        Set<Ingrediente> ingredientes = new HashSet<>(ingredienteRepository.findAllById(dto.ingredientes));
        pizza.setIngredientes(ingredientes);

        pizzaRepository.save(pizza);
        return ResponseEntity.ok(pizza);
    }

    // Actualizar pizza
    @PutMapping("/{id}")
    public ResponseEntity<Pizza> actualizarPizza(@PathVariable Long id, @RequestBody Pizza pizza) {
        try {
            Pizza pizzaActualizada = pizzaService.actualizarPizza(id, pizza);
            return ResponseEntity.ok(pizzaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar pizza
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPizza(@PathVariable Long id) {
        try {
            pizzaService.eliminarPizza(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar pizzas por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Pizza>> buscarPizzasPorTipo(@PathVariable enumPizza tipo) {
        try {
            List<Pizza> pizzas = pizzaService.buscarPizzasPorTipo(tipo);
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar pizzas por tipo de masa
    @GetMapping("/masa/{tipoMasa}")
    public ResponseEntity<List<Pizza>> buscarPizzasPorTipoMasa(@PathVariable enumMassa tipoMasa) {
        try {
            List<Pizza> pizzas = pizzaService.buscarPizzasPorTipoMasa(tipoMasa);
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar pizzas por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Pizza>> buscarPizzasPorNombre(@RequestParam String nombre) {
        try {
            List<Pizza> pizzas = pizzaService.buscarPizzasPorNombre(nombre);
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pizzas sin gluten
    @GetMapping("/sin-gluten")
    public ResponseEntity<List<Pizza>> buscarPizzasSinGluten() {
        try {
            List<Pizza> pizzas = pizzaService.buscarPizzasSinGluten();
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar pizzas por precio menor que
    @GetMapping("/precio-menor/{precio}")
    public ResponseEntity<List<Pizza>> buscarPizzasPorPrecioMenorQue(@PathVariable Double precio) {
        try {
            List<Pizza> pizzas = pizzaService.buscarPizzasPorPrecioMenorQue(precio);
            return ResponseEntity.ok(pizzas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}