package com.example.demoGestoriaPizzeria.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PizzaDTO {
    private String nombre;
    private String descripcion;
    private String tipo;
    private String tipoMasa;
    private boolean esSinGluten;
    private double precio;
    private int cantidad;
    private List<Long> ingredientes;
}