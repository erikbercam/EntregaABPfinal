package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.Model.Direccion;
import com.example.demoGestoriaPizzeria.Services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> obtenerTodasLasDirecciones() {
        List<Direccion> direcciones = direccionService.obtenerTodasLasDirecciones();
        return ResponseEntity.ok(direcciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccionPorId(@PathVariable Long id) {
        Optional<Direccion> direccion = direccionService.obtenerDireccionPorId(id);
        return direccion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Direccion> crearDireccion(@RequestBody Direccion direccion) {
        Direccion nuevaDireccion = direccionService.guardarDireccion(direccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaDireccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable Long id, @RequestBody Direccion direccion) {
        try {
            Direccion direccionActualizada = direccionService.actualizarDireccion(id, direccion);
            return ResponseEntity.ok(direccionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Direccion>> buscarDireccionesPorCiudad(@PathVariable String ciudad) {
        List<Direccion> direcciones = direccionService.buscarDireccionesPorCiudad(ciudad);
        return ResponseEntity.ok(direcciones);
    }

    @GetMapping("/codigo-postal/{codigoPostal}")
    public ResponseEntity<List<Direccion>> buscarDireccionesPorCodigoPostal(@PathVariable String codigoPostal) {
        List<Direccion> direcciones = direccionService.buscarDireccionesPorCodigoPostal(codigoPostal);
        return ResponseEntity.ok(direcciones);
    }

    @GetMapping("/buscar-calle")
    public ResponseEntity<List<Direccion>> buscarDireccionesPorCalle(@RequestParam String calle) {
        List<Direccion> direcciones = direccionService.buscarDireccionesPorCalle(calle);
        return ResponseEntity.ok(direcciones);
    }
}