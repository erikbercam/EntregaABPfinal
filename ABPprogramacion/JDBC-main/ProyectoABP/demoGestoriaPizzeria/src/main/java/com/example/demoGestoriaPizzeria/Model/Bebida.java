package com.example.demoGestoriaPizzeria.Model;
import com.example.demoGestoriaPizzeria.Enums.TamanoBebida;
import com.example.demoGestoriaPizzeria.Model.Producto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Bebida extends Producto {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TamanoBebida tamano;

    @Column(nullable = false)
    private boolean disponible = true;

    // Constructor vacío requerido por JPA
    public Bebida() {}

    // Constructor con parámetros
    public Bebida(String nombre, Double precio, TamanoBebida tamano) {
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.tamano = tamano;
        this.disponible = true;
        this.setCantidad(0);
    }
}