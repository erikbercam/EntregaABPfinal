package com.example.demoGestoriaPizzeria.DTO;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class PedidoDTO {
    private Long clienteId;
    private List<DetallePedidoDTO> detalles;
    private String instruccionesEspeciales;
}

@Data
class DetallePedidoDTO {
    private Long pizzaId;
    private int cantidad;
    private String instrucciones;
}