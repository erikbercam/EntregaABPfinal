package com.example.demoGestoriaPizzeria.Model;

import com.example.demoGestoriaPizzeria.Enums.enumRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private enumRol rol;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}