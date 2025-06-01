package com.example.demoGestoriaPizzeria.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nombre;
    private int cantidad;

    private boolean esIngredienteSinGluten;
    private boolean esVegano;

    @ManyToMany(mappedBy = "ingredientes")
    @EqualsAndHashCode.Exclude  // ⚠️ ¡ESTO es CLAVE!
    @ToString.Exclude
    @JsonIgnore
    private Set<Pizza> pizzas;
}
