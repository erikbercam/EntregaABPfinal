package com.example.demoGestoriaPizzeria.Model;

import com.example.demoGestoriaPizzeria.Enums.enumEstadoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    private Double total;

    @Enumerated(EnumType.STRING)
    private enumEstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado repartidor;

    private double precio;

    @ManyToMany(mappedBy = "pedidos")
    private Set<Producto> productos;
}