package com.example.demoGestoriaPizzeria.DTO;

import com.example.demoGestoriaPizzeria.Enums.enumEstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequestDTO {
    private LocalDateTime fechaPedido;
    private Double total;
    private enumEstadoPedido estado;
    private Long clienteId;
    private Long repartidorId;
    private Double precio;
    private List<Long> productoIds;

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(Long repartidorId) {
        this.repartidorId = repartidorId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public enumEstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(enumEstadoPedido estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
