package com.example.demoGestoriaPizzeria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int cantidad;
    private boolean esVegano;
    private boolean esIngredienteSinGluten;

    @ManyToMany(mappedBy = "ingredientes")
    private Set<Pizza> pizzas;
}
