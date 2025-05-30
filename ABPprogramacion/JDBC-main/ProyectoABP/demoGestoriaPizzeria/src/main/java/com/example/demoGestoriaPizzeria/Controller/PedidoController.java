//package com.example.demoGestoriaPizzeria.Controller;

//import com.example.demoGestoriaPizzeria.Model.Pedido;
//import com.example.demoGestoriaPizzeria.Services.PedidoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/pedidos")
//public class PedidoController {
//
//    @Autowired
//    private PedidoService pedidoService;
//
//    @GetMapping
//    public List<Pedido> obtenerTodosLosPedidos() {
//        return pedidoService.obtenerTodosLosPedidos();
//    }
//
//    @GetMapping("/{id}")
//    public Pedido obtenerPedidoPorId(@PathVariable Long id) {
//        return pedidoService.obtenerPedidoPorId(id);
//    }
//
//    @PostMapping
//    public Pedido crearPedido(@RequestBody Pedido pedido) {
//        return pedidoService.crearPedido(pedido);
//    }
//
//    @PutMapping("/{id}")
//    public Pedido actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
//        return pedidoService.actualizarPedido(id, pedido);
//    }
//
//    @DeleteMapping("/{id}")
//    public void eliminarPedido(@PathVariable Long id) {
//        pedidoService.eliminarPedido(id);
//    }
//
//    @GetMapping("/estado/{estado}")
//    public List<Pedido> obtenerPedidosPorEstado(@PathVariable String estado) {
//        return pedidoService.obtenerPedidosPorEstado(estado);
//    }
//}

package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.DTO.PedidoRequestDTO;
import com.example.demoGestoriaPizzeria.Enums.enumEstadoPedido;
import com.example.demoGestoriaPizzeria.Model.Cliente;
import com.example.demoGestoriaPizzeria.Model.Empleado;
import com.example.demoGestoriaPizzeria.Model.Pedido;
import com.example.demoGestoriaPizzeria.Model.Producto;
import com.example.demoGestoriaPizzeria.Repository.ClienteRepository;
import com.example.demoGestoriaPizzeria.Repository.EmpleadoRepository;
import com.example.demoGestoriaPizzeria.Repository.ProductoRepository;
import com.example.demoGestoriaPizzeria.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demoGestoriaPizzeria.Repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            Optional<Pedido> pedido = pedidoService.obtenerPedidoPorId(id);
            return pedido.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequestDTO dto) {
        try {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            Empleado repartidor = empleadoRepository.findById(dto.getRepartidorId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            Set<Producto> productos = dto.getProductoIds().stream()
                    .map(id -> productoRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id)))
                    .collect(Collectors.toSet());

            Pedido pedido = new Pedido();
            pedido.setFechaPedido(dto.getFechaPedido());
            pedido.setTotal(dto.getTotal());
            pedido.setEstado(dto.getEstado());
            pedido.setCliente(cliente);
            pedido.setRepartidor(repartidor);
            pedido.setPrecio(dto.getPrecio());
            pedido.setProductos(productos);

            Pedido guardado = pedidoRepository.save(pedido);
            return ResponseEntity.ok(guardado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al crear el pedido");
        }
    }
    // Actualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarPedido(id, pedido);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorEstado(@PathVariable enumEstadoPedido estado) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Cambiar estado del pedido
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstadoPedido(@PathVariable Long id, @RequestBody enumEstadoPedido nuevoEstado) {
        try {
            Pedido pedidoActualizado = pedidoService.cambiarEstadoPedido(id, nuevoEstado);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Asignar repartidor a pedido
    @PatchMapping("/{pedidoId}/repartidor/{repartidorId}")
    public ResponseEntity<Pedido> asignarRepartidor(@PathVariable Long pedidoId, @PathVariable Long repartidorId) {
        try {
            Pedido pedidoActualizado = pedidoService.asignarRepartidor(pedidoId, repartidorId);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por cliente
    @PostMapping("/cliente")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorCliente(@RequestBody Cliente cliente) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(cliente);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por cliente ordenados por fecha
    @PostMapping("/cliente/ordenados")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorClienteOrdenados(@RequestBody Cliente cliente) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorClienteOrdenados(cliente);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por repartidor
    @PostMapping("/repartidor")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorRepartidor(@RequestBody Empleado repartidor) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorRepartidor(repartidor);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por rango de fechas
    @GetMapping("/rango-fecha")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorRangoFecha(fechaInicio, fechaFin);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por monto mínimo
    @GetMapping("/monto-minimo/{montoMinimo}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorMontoMinimo(@PathVariable Double montoMinimo) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorMontoMinimo(montoMinimo);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener pedidos por fecha y estado
    @GetMapping("/fecha-estado")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorFechaYEstado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam enumEstadoPedido estado) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorFechaYEstado(fecha, estado);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Calcular ventas por período
    @GetMapping("/ventas-periodo")
    public ResponseEntity<Double> calcularVentasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            Double ventas = pedidoService.calcularVentasPorPeriodo(fechaInicio, fechaFin);
            return ResponseEntity.ok(ventas != null ? ventas : 0.0);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}