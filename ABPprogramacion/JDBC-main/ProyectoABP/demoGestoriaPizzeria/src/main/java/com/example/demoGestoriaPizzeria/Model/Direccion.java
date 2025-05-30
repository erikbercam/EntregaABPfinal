package com.example.demoGestoriaPizzeria.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private String numero;
    private String ciudad;
    private String codigoPostal;

    @OneToMany(mappedBy = "direccion")
    private List<Persona> personas;
}