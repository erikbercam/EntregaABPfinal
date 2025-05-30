package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocumento(String documento);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByTelefono(String telefono);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT c FROM Cliente c WHERE c.pedidos IS NOT EMPTY")
    List<Cliente> findClientesConPedidos();
}




