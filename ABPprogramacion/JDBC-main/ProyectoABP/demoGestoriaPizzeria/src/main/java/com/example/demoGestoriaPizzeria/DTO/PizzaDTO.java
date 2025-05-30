package com.example.demoGestoriaPizzeria.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PizzaDTO {
    public String nombre;
    public String descripcion;
    public String tipo;
    public String tipoMasa;
    public boolean esSinGluten;
    public double precio;
    public int cantidad;
    public List<Long> ingredientes;
}