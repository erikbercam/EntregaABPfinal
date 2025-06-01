package com.example.demoGestoriaPizzeria.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String documento;
    private String email;
    private String telefono;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    @JsonIgnore
    private Direccion direccion;
}
