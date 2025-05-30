//package com.example.demoGestoriaPizzeria.Enums;
//
//public enum enumBebidaTamaño {
//    PEQUEÑO,
//    MEDIANO,
//    GRANDE
//}

package com.example.demoGestoriaPizzeria.Enums;

public enum TamanoBebida {
    PEQUEÑO(200, "S"),
    MEDIANO(350, "M"),
    GRANDE(500, "L");

    private final int mililitros;
    private final String codigo;

    TamanoBebida(int mililitros, String codigo) {
        this.mililitros = mililitros;
        this.codigo = codigo;
    }

    public int getMililitros() { return mililitros; }
    public String getCodigo() { return codigo; }
}
