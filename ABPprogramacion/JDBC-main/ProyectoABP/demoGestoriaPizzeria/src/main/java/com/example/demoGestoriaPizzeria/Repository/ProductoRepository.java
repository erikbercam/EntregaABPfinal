package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByPrecioBetween(double precioMin, double precioMax);
    List<Producto> findByCantidadGreaterThan(int cantidad);

    @Query("SELECT p FROM Producto p ORDER BY p.precio DESC")
    List<Producto> findAllOrderByPrecioDesc();
}