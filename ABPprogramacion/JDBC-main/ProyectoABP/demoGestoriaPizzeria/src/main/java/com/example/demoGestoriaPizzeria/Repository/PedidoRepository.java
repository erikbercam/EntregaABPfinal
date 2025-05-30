package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Enums.enumEstadoPedido;
import com.example.demoGestoriaPizzeria.Model.Cliente;
import com.example.demoGestoriaPizzeria.Model.Empleado;
import com.example.demoGestoriaPizzeria.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByEstado(enumEstadoPedido estado);
    List<Pedido> findByClienteOrderByFechaPedidoDesc(Cliente cliente);
    List<Pedido> findByRepartidor(Empleado repartidor);
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Pedido> findByTotalGreaterThanEqual(Double total);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido >= :fecha AND p.estado = :estado")
    List<Pedido> findPedidosPorFechaYEstado(@Param("fecha") LocalDateTime fecha, @Param("estado") enumEstadoPedido estado);

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    Double calcularVentasPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
