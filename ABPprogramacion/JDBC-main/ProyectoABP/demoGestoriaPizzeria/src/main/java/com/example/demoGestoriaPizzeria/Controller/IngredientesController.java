package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.Model.Ingrediente;
import com.example.demoGestoriaPizzeria.Services.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredientesController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> obtenerTodosLosIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.obtenerTodosLosIngredientes();
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> obtenerIngredientePorId(@PathVariable Long id) {
        Optional<Ingrediente> ingrediente = ingredienteService.obtenerIngredientePorId(id);
        return ingrediente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ingrediente> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente nuevoIngrediente = ingredienteService.guardarIngrediente(ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoIngrediente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        try {
            Ingrediente ingredienteActualizado = ingredienteService.actualizarIngrediente(id, ingrediente);
            return ResponseEntity.ok(ingredienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Long id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sin-gluten")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesSinGluten() {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesSinGluten();
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/veganos")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesVeganos() {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesVeganos();
        return ResponseEntity.ok(ingredientes);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStockIngrediente(@PathVariable Long id, @RequestParam int cantidad) {
        ingredienteService.actualizarStockIngrediente(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesPorNombre(@RequestParam String nombre) {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesPorNombre(nombre);
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/con-stock")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesConStock(@RequestParam int cantidadMinima) {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesConStock(cantidadMinima);
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/poco-stock")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesConPocoStock(@RequestParam int cantidadMaxima) {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesConPocoStock(cantidadMaxima);
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/agotados")
    public ResponseEntity<List<Ingrediente>> obtenerIngredientesAgotados() {
        List<Ingrediente> ingredientes = ingredienteService.obtenerIngredientesAgotados();
        return ResponseEntity.ok(ingredientes);
    }
}