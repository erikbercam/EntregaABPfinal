package com.example.demoGestoriaPizzeria.Model;

import com.example.demoGestoriaPizzeria.Enums.enumMassa;
import com.example.demoGestoriaPizzeria.Enums.enumPizza;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Pizza extends Producto {
//    private Long id;
    @Enumerated(EnumType.STRING)
    private enumPizza tipo;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private enumMassa tipoMasa;
    private boolean esSinGluten;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingrediente",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private Set<Ingrediente> ingredientes;
}