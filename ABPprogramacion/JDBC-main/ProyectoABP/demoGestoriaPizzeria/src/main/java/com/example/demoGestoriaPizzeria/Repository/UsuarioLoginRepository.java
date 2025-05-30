package com.example.demoGestoriaPizzeria.Repository;

import com.example.demoGestoriaPizzeria.Model.UsuarioLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioLoginRepository extends JpaRepository<UsuarioLogin, Long> {
    Optional<UsuarioLogin> findByUsername(String username);
    Optional<UsuarioLogin> findByUsernameAndPassword(String username, String password);
}