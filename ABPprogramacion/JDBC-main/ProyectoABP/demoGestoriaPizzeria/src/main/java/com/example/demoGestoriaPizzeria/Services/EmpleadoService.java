package com.example.demoGestoriaPizzeria.Services;

import com.example.demoGestoriaPizzeria.Enums.enumPuestoTrabajador;
import com.example.demoGestoriaPizzeria.Model.Empleado;
import com.example.demoGestoriaPizzeria.Model.Pedido;
import com.example.demoGestoriaPizzeria.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) {
        return empleadoRepository.findById(id)
                .map(empleadoExistente -> {
                    empleadoExistente.setNombre(empleadoActualizado.getNombre());
                    empleadoExistente.setDocumento(empleadoActualizado.getDocumento());
                    empleadoExistente.setEmail(empleadoActualizado.getEmail());
                    empleadoExistente.setTelefono(empleadoActualizado.getTelefono());
                    empleadoExistente.setDireccion(empleadoActualizado.getDireccion());
                    empleadoExistente.setSueldo(empleadoActualizado.getSueldo());
                    empleadoExistente.setActivo(empleadoActualizado.isActivo());
                    return empleadoRepository.save(empleadoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    public void desactivarEmpleado(Long id) {
        empleadoRepository.findById(id)
                .ifPresent(empleado -> {
                    empleado.setActivo(false);
                    empleadoRepository.save(empleado);
                });
    }

    public List<Empleado> obtenerEmpleadosPorPuesto(enumPuestoTrabajador puesto) {
        return empleadoRepository.findByPuesto(puesto);
    }

    public List<Empleado> obtenerEmpleadosActivos() {
        return empleadoRepository.findByActivoTrue();
    }

    public void asignarPedidoAEmpleado(Long empleadoId, Pedido pedido) {
        empleadoRepository.findById(empleadoId)
                .ifPresent(empleado -> {
                    empleado.asignarPedido(pedido);
                    empleadoRepository.save(empleado);
                });
    }

    public Optional<Empleado> buscarPorDocumento(String documento) {
        return empleadoRepository.findByDocumento(documento);
    }

    // ============= MÉTODOS CORREGIDOS =============

    // CORRECCIÓN: Solo un parámetro para findByPuestoAndActivoTrue
    public List<Empleado> obtenerEmpleadosActivosPorPuesto(enumPuestoTrabajador puesto) {
        return empleadoRepository.findByPuestoAndActivoTrue(puesto);  // ✅ Solo un parámetro
    }

    // Si necesitas controlar el valor de 'activo', usa este método alternativo
    public List<Empleado> obtenerEmpleadosPorPuestoYEstado(enumPuestoTrabajador puesto, boolean activo) {
        return empleadoRepository.findByPuestoAndActivo(puesto, activo);  // ✅ Dos parámetros
    }

    // Métodos adicionales que necesitarás definir en el repository
    public List<Empleado> obtenerEmpleadosPorSueldoMinimo(double sueldoMinimo) {
        // Necesitarás añadir este método al repository
        return empleadoRepository.findBySueldoGreaterThanEqual(sueldoMinimo);
    }

    public List<Empleado> obtenerRepartidoresDisponibles(int maxPedidos) {
        // Necesitarás añadir este método al repository con @Query
        return empleadoRepository.findRepartidoresDisponibles(maxPedidos);
    }
}