package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Empleado;
import com.example.demoGestoriaPizzeria.Enums.enumPuestoTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // ============= MÉTODOS BÁSICOS =============

    // Buscar por puesto (sin filtro de activo)
    List<Empleado> findByPuesto(enumPuestoTrabajador puesto);

    // Buscar solo empleados activos
    List<Empleado> findByActivoTrue();

    // Buscar por puesto Y que esté activo (SOLO UN PARÁMETRO)
    List<Empleado> findByPuestoAndActivoTrue(enumPuestoTrabajador puesto);

    // Buscar por puesto Y estado específico (DOS PARÁMETROS)
    List<Empleado> findByPuestoAndActivo(enumPuestoTrabajador puesto, boolean activo);

    // Buscar por documento
    Optional<Empleado> findByDocumento(String documento);

    // Buscar por email
    Optional<Empleado> findByEmail(String email);

    // ============= MÉTODOS POR SUELDO =============

    // Empleados con sueldo mayor o igual a un mínimo
    List<Empleado> findBySueldoGreaterThanEqual(Double sueldoMinimo);

    // Empleados con sueldo en un rango
    List<Empleado> findBySueldoBetween(Double sueldoMin, Double sueldoMax);

    // ============= QUERIES PERSONALIZADAS =============

    @Query("SELECT e FROM Empleado e WHERE e.puesto = 'REPARTIDOR' AND e.activo = true AND SIZE(e.pedidos) < :maxPedidos")
    List<Empleado> findRepartidoresDisponibles(@Param("maxPedidos") int maxPedidos);

    // Query alternativa para empleados activos por puesto
    @Query("SELECT e FROM Empleado e WHERE e.puesto = :puesto AND e.activo = true")
    List<Empleado> findEmpleadosActivosPorPuesto(@Param("puesto") enumPuestoTrabajador puesto);

    // Query para obtener empleados por rango de salario y activos
    @Query("SELECT e FROM Empleado e WHERE e.sueldo BETWEEN :salarioMin AND :salarioMax AND e.activo = true")
    List<Empleado> findByRangoSalarioAndActivoTrue(@Param("salarioMin") Double salarioMin,
                                                   @Param("salarioMax") Double salarioMax);

    // Query para contar empleados por puesto
    @Query("SELECT COUNT(e) FROM Empleado e WHERE e.puesto = :puesto AND e.activo = true")
    Long countEmpleadosActivosPorPuesto(@Param("puesto") enumPuestoTrabajador puesto);
}