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

import java.util.*;

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
    public ResponseEntity<?> crearPizza(@RequestBody PizzaDTO dto) {
        try {
            // Validaciones básicas
            if (dto.getNombre() == null || dto.getNombre().isBlank()) {
                return ResponseEntity.badRequest().body("El nombre es obligatorio.");
            }
            if (dto.getTipo() == null || dto.getTipoMasa() == null) {
                return ResponseEntity.badRequest().body("Tipo de pizza y tipo de masa son obligatorios.");
            }

            enumPizza tipoEnum;
            enumMassa tipoMasaEnum;

            try {
                tipoEnum = enumPizza.valueOf(dto.getTipo().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Tipo de pizza inválido: " + dto.getTipo());
            }

            try {
                tipoMasaEnum = enumMassa.valueOf(dto.getTipoMasa().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Tipo de masa inválido: " + dto.getTipoMasa());
            }

            // Validar ingredientes
            if (dto.getIngredientes() == null || dto.getIngredientes().isEmpty()) {
                return ResponseEntity.badRequest().body("Debe proporcionar al menos un ingrediente.");
            }

            // ✅ SOLUCIÓN CORRECTA: Copiar la lista antes de convertirla en set
            List<Ingrediente> ingredientesFromDb = new ArrayList<>(ingredienteRepository.findAllById(dto.getIngredientes()));
            if (ingredientesFromDb.size() != dto.getIngredientes().size()) {
                return ResponseEntity.badRequest().body("Uno o más IDs de ingredientes no existen.");
            }

            Set<Ingrediente> ingredientes = new HashSet<>(ingredientesFromDb);

            // Crear y guardar la pizza
            Pizza pizza = new Pizza();
            pizza.setNombre(dto.getNombre());
            pizza.setDescripcion(dto.getDescripcion());
            pizza.setTipo(tipoEnum);
            pizza.setTipoMasa(tipoMasaEnum);
            pizza.setEsSinGluten(dto.isEsSinGluten());
            pizza.setPrecio(dto.getPrecio());
            pizza.setCantidad(dto.getCantidad());
            pizza.setIngredientes(ingredientes);

            pizzaRepository.save(pizza);
            return ResponseEntity.status(HttpStatus.CREATED).body(pizza);

        } catch (Exception e) {
            e.printStackTrace();  // ✅ IMPORTANTE: muestra error real en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la pizza: " + e.getClass().getSimpleName() + " - " +
                            (e.getMessage() != null ? e.getMessage() : "sin mensaje"));
        }
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

    @GetMapping("/tipos")
    public enumPizza[] obtenerTiposPizza() {
        return enumPizza.values();
    }

    @GetMapping("/masas")
    public enumMassa[] obtenerTiposMasa() {
        return enumMassa.values();
    }
}