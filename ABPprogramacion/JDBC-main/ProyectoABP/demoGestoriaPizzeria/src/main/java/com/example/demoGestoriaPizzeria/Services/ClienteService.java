//package com.example.demoGestoriaPizzeria.Services;
//
//import com.example.demoGestoriaPizzeria.Model.Cliente;
//import com.example.demoGestoriaPizzeria.Model.Pedido;
//import com.example.demoGestoriaPizzeria.Repository.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ClienteService {
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    public List<Cliente> obtenerTodosLosClientes() {
//        return clienteRepository.findAll();
//    }
//
//    public Optional<Cliente> obtenerClientePorId(Long id) {
//        return clienteRepository.findById(id);
//    }
//
//    public Cliente crearCliente(Cliente cliente) {
//        return clienteRepository.save(cliente);
//    }
//
//    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
//        return clienteRepository.findById(id)
//                .map(clienteExistente -> {
//                    clienteExistente.setNombre(clienteActualizado.getNombre());
//                    clienteExistente.setEmail(clienteActualizado.getEmail());
//                    clienteExistente.setTelefono(clienteActualizado.getTelefono());
//                    clienteExistente.setDireccion(clienteActualizado.getDireccion());
//                    return clienteRepository.save(clienteExistente);
//                })
//                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
//    }
//}package com.example.demoGestoriaPizzeria.Service;
//
//import com.example.demoGestoriaPizzeria.Model.Cliente;
//import com.example.demoGestoriaPizzeria.Repository.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ClienteService {
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    public List<Cliente> obtenerTodosLosClientes() {
//        return clienteRepository.findAll();
//    }
//
//    public Optional<Cliente> obtenerClientePorId(Long id) {
//        return clienteRepository.findById(id);
//    }
//
//    public Cliente guardarCliente(Cliente cliente) {
//        return clienteRepository.save(cliente);
//    }
//
//    public void eliminarCliente(Long id) {
//        clienteRepository.deleteById(id);
//    }
//
//    public Optional<Cliente> buscarPorDocumento(String documento) {
//        return clienteRepository.findByDocumento(documento);
//    }
//}
package com.example.demoGestoriaPizzeria.Services;

import com.example.demoGestoriaPizzeria.Model.Cliente;
import com.example.demoGestoriaPizzeria.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(clienteActualizado.getNombre());
                    clienteExistente.setDocumento(clienteActualizado.getDocumento());
                    clienteExistente.setEmail(clienteActualizado.getEmail());
                    clienteExistente.setTelefono(clienteActualizado.getTelefono());
                    clienteExistente.setDireccion(clienteActualizado.getDireccion());
                    return clienteRepository.save(clienteExistente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clienteRepository.findByDocumento(documento);
    }

    // NUEVOS MÉTODOS basados en el repository
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> buscarPorTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }

    public List<Cliente> buscarClientesPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Cliente> obtenerClientesConPedidos() {
        return clienteRepository.findClientesConPedidos();
    }
}