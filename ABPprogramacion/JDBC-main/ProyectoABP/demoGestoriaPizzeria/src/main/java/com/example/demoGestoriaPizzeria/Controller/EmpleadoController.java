//package com.example.demoGestoriaPizzeria.Controller;

//import com.example.demoGestoriaPizzeria.Model.Empleado;
//import com.example.demoGestoriaPizzeria.Services.EmpleadoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/empleados")
//public class EmpleadoController {
//
//    @Autowired
//    private EmpleadoService empleadoService;
//
//    @GetMapping
//    public List<Empleado> obtenerTodosLosEmpleados() {
//        return empleadoService.obtenerTodosLosEmpleados();
//    }
//
//    @GetMapping("/{id}")
//    public Empleado obtenerEmpleadoPorId(@PathVariable Long id) {
//        return empleadoService.obtenerEmpleadoPorId(id);
//    }
//
//    @PostMapping
//    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
//        return empleadoService.crearEmpleado(empleado);
//    }
//
//    @PutMapping("/{id}")
//    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
//        return empleadoService.actualizarEmpleado(id, empleado);
//    }
//
//    @DeleteMapping("/{id}")
//    public void desactivarEmpleado(@PathVariable Long id) {
//        empleadoService.desactivarEmpleado(id);
//    }
//
////    @GetMapping("/puesto/{puesto}")
////    public List<Empleado> obtenerEmpleadosPorPuesto(@PathVariable String puesto) {
////        return empleadoService.obtenerEmpleadosPorPuesto(puesto);
////    }
//}


package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.Enums.enumPuestoTrabajador;
import com.example.demoGestoriaPizzeria.Model.Empleado;
import com.example.demoGestoriaPizzeria.Services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.obtenerEmpleadoPorId(id);
        return empleado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        Empleado nuevoEmpleado = empleadoService.guardarEmpleado(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(id, empleado);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarEmpleado(@PathVariable Long id) {
        empleadoService.desactivarEmpleado(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/puesto/{puesto}")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorPuesto(@PathVariable enumPuestoTrabajador puesto) {
        List<Empleado> empleados = empleadoService.obtenerEmpleadosPorPuesto(puesto);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosActivos() {
        List<Empleado> empleados = empleadoService.obtenerEmpleadosActivos();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Empleado> buscarPorDocumento(@PathVariable String documento) {
        Optional<Empleado> empleado = empleadoService.buscarPorDocumento(documento);
        return empleado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sueldo-minimo")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorSueldoMinimo(@RequestParam double sueldo) {
        List<Empleado> empleados = empleadoService.obtenerEmpleadosPorSueldoMinimo(sueldo);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/repartidores-disponibles")
    public ResponseEntity<List<Empleado>> obtenerRepartidoresDisponibles(@RequestParam int maxPedidos) {
        List<Empleado> repartidores = empleadoService.obtenerRepartidoresDisponibles(maxPedidos);
        return ResponseEntity.ok(repartidores);
    }
}