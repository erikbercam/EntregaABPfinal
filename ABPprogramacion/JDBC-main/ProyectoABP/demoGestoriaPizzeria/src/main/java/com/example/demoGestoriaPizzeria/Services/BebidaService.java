//package com.example.demoGestoriaPizzeria.Services;
//
//import com.example.demoGestoriaPizzeria.Model.Bebida;
//import com.example.demoGestoriaPizzeria.Repository.BebidaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BebidaService {
//
//    private final BebidaRepository bebidaRepository;
//
//    @Autowired
//    public BebidaService(BebidaRepository bebidaRepository) {
//        this.bebidaRepository = bebidaRepository;
//    }
//
//    public List<Bebida> obtenerTodasLasBebidas() {
//        return bebidaRepository.findAll();
//    }
//
//    public Optional<Bebida> obtenerBebidaPorId(Long id) {
//        return bebidaRepository.findById(id);
//    }
//
//    public Bebida crearBebida(Bebida bebida) {
//        return bebidaRepository.save(bebida);
//    }
//
//    public Bebida actualizarBebida(Long id, Bebida bebidaActualizada) {
//        return bebidaRepository.findById(id)
//                .map(bebidaExistente -> {
//                    bebidaExistente.setNombre(bebidaActualizada.getNombre());
//                    bebidaExistente.setTamano(bebidaActualizada.getTamano());
//                    bebidaExistente.setDisponible(bebidaActualizada.isDisponible());
//                    bebidaExistente.setPrecio(bebidaActualizada.getPrecio());
//                    return bebidaRepository.save(bebidaExistente);
//                })
//                .orElseThrow(() -> new RuntimeException("Bebida no encontrada con id: " + id));
//    }
//
//    public void eliminarBebida(Long id) {
//        bebidaRepository.deleteById(id);
//    }
//
////    public List<Bebida> obtenerBebidasPorTamano(Bebida.TamanoEnum tamano) {
////        return bebidaRepository.findByTamano(tamano);
////    }
//
//    public List<Bebida> obtenerBebidasDisponibles() {
//        return bebidaRepository.findByDisponibleTrue();
//    }
//}
package com.example.demoGestoriaPizzeria.Services;

import com.example.demoGestoriaPizzeria.Enums.TamanoBebida;
import com.example.demoGestoriaPizzeria.Model.Bebida;
import com.example.demoGestoriaPizzeria.Repository.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BebidaService {

    private final BebidaRepository bebidaRepository;

    @Autowired
    public BebidaService(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    public List<Bebida> obtenerTodasLasBebidas() {
        return bebidaRepository.findAll();
    }

    public Optional<Bebida> obtenerBebidaPorId(Long id) {
        return bebidaRepository.findById(id);
    }

    public Bebida guardarBebida(Bebida bebida) {
        // Validación: evitar duplicados
        Optional<Bebida> existente = bebidaRepository
                .findByNombreAndTamano(bebida.getNombre(), bebida.getTamano());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una bebida con el mismo nombre y tamaño");
        }

        return bebidaRepository.save(bebida);
    }

    public Bebida actualizarBebida(Long id, Bebida bebidaActualizada) {
        return bebidaRepository.findById(id)
                .map(bebidaExistente -> {
                    bebidaExistente.setNombre(bebidaActualizada.getNombre());
                    bebidaExistente.setPrecio(bebidaActualizada.getPrecio());
                    bebidaExistente.setCantidad(bebidaActualizada.getCantidad());
                    bebidaExistente.setTamano(bebidaActualizada.getTamano());
                    bebidaExistente.setDisponible(bebidaActualizada.isDisponible());
                    return bebidaRepository.save(bebidaExistente);
                })
                .orElseThrow(() -> new RuntimeException("Bebida no encontrada con id: " + id));
    }

    public void eliminarBebida(Long id) {
        if (!bebidaRepository.existsById(id)) {
            throw new RuntimeException("Bebida no encontrada con id: " + id);
        }
        bebidaRepository.deleteById(id);
    }

    public List<Bebida> obtenerBebidasPorTamano(TamanoBebida tamano) {
        return bebidaRepository.findByTamano(tamano);
    }

    public List<Bebida> obtenerBebidasDisponibles() {
        return bebidaRepository.findByDisponibleTrue();
    }

    // Método mejorado: solo bebidas disponibles con stock
    public List<Bebida> obtenerBebidasEnStock() {
        return bebidaRepository.findByDisponibleTrueAndCantidadGreaterThan(0);
    }

    public List<Bebida> buscarBebidasPorNombre(String nombre) {
        return bebidaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Optional<Bebida> buscarBebidaPorNombre(String nombre) {
        return bebidaRepository.findByNombre(nombre);
    }

    // Método adicional útil
    public boolean hayStock(Long id, Integer cantidadRequerida) {
        return bebidaRepository.findById(id)
                .map(bebida -> bebida.isDisponible() && bebida.getCantidad() >= cantidadRequerida)
                .orElse(false);
    }
}