package com.example.demoGestoriaPizzeria.Model;

import com.example.demoGestoriaPizzeria.Enums.enumPuestoTrabajador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado extends Persona{

    @Enumerated(EnumType.STRING)
    private enumPuestoTrabajador puesto;

    private double sueldo;
    private boolean activo;

    @OneToMany(mappedBy = "repartidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    public void asignarPedido(Pedido pedido) {
        if (pedidos == null) {
            pedidos = new ArrayList<>();
        }
        pedidos.add(pedido);
        pedido.setRepartidor(this);
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidos;
    }

    // Método que devuelve el enum
    public enumPuestoTrabajador getPuesto() {
        return puesto;
    }

    // Método adicional que devuelve String si lo necesitas
    public String getPuestoAsString() {
        return puesto != null ? puesto.toString() : null;
    }
}