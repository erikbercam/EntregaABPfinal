//package com.example.demoGestoriaPizzeria.Controller;

//import com.example.demoGestoriaPizzeria.Model.Cliente;
//import com.example.demoGestoriaPizzeria.Services.ClienteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/clientes")
//public class ClienteController {
//
//    @Autowired
//    private ClienteService clienteService;
//
//    @GetMapping
//    public List<Cliente> obtenerTodosLosClientes() {
//        return clienteService.obtenerTodosLosClientes();
//    }
//
//    @GetMapping("/{id}")
//    public Cliente obtenerClientePorId(@PathVariable Long id) {
//        return clienteService.obtenerClientePorId(id);
//    }
//
//    @PostMapping
//    public Cliente crearCliente(@RequestBody Cliente cliente) {
//        return clienteService.crearCliente(cliente);
//    }
//
//    @PutMapping("/{id}")
//    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
//        return clienteService.actualizarCliente(id, cliente);
//    }
//
//    @DeleteMapping("/{id}")
//    public void eliminarCliente(@PathVariable Long id) {
//        clienteService.eliminarCliente(id);
//    }
//
//    @GetMapping("/buscar")
//    public List<Cliente> buscarClientesPorNombre(@RequestParam String nombre) {
//        return clienteService.buscarClientesPorNombre(nombre);
//    }
//}

package com.example.demoGestoriaPizzeria.Controller;

import com.example.demoGestoriaPizzeria.Model.Cliente;
import com.example.demoGestoriaPizzeria.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Cliente> buscarPorDocumento(@PathVariable String documento) {
        Optional<Cliente> cliente = clienteService.buscarPorDocumento(documento);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<Cliente> buscarPorTelefono(@PathVariable String telefono) {
        Optional<Cliente> cliente = clienteService.buscarPorTelefono(telefono);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientesPorNombre(@RequestParam String nombre) {
        List<Cliente> clientes = clienteService.buscarClientesPorNombre(nombre);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/con-pedidos")
    public ResponseEntity<List<Cliente>> obtenerClientesConPedidos() {
        List<Cliente> clientes = clienteService.obtenerClientesConPedidos();
        return ResponseEntity.ok(clientes);
    }
}