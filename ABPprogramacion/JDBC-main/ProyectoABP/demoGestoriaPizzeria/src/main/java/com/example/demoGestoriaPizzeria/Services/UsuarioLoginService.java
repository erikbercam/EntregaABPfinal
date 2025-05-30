package com.example.demoGestoriaPizzeria.Services;

import com.example.demoGestoriaPizzeria.Model.UsuarioLogin;
import com.example.demoGestoriaPizzeria.Repository.UsuarioLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioLoginService {

    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;

    /**
     * Guarda un nuevo usuario en la base de datos
     * @param usuarioLogin el usuario a guardar
     * @return el usuario guardado
     */
    public UsuarioLogin guardarUsuario(UsuarioLogin usuarioLogin) {
        // Verificar si el username ya existe
        Optional<UsuarioLogin> usuarioExistente = usuarioLoginRepository.findByUsername(usuarioLogin.getUsername());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe: " + usuarioLogin.getUsername());
        }

        return usuarioLoginRepository.save(usuarioLogin);
    }

    /**
     * Busca un usuario por su username
     * @param username el nombre de usuario a buscar
     * @return Optional con el usuario si existe
     */
    public Optional<UsuarioLogin> buscarPorUsername(String username) {
        return usuarioLoginRepository.findByUsername(username);
    }

    /**
     * Autentica un usuario con username y password
     * @param username nombre de usuario
     * @param password contraseña
     * @return Optional con el usuario si las credenciales son válidas
     */
    public Optional<UsuarioLogin> autenticarUsuario(String username, String password) {
        return usuarioLoginRepository.findByUsernameAndPassword(username, password);
    }

    /**
     * Obtiene todos los usuarios
     * @return lista de todos los usuarios
     */
    public List<UsuarioLogin> obtenerTodosLosUsuarios() {
        return usuarioLoginRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     * @param id el ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<UsuarioLogin> buscarPorId(Long id) {
        return usuarioLoginRepository.findById(id);
    }

    /**
     * Actualiza un usuario existente
     * @param id el ID del usuario a actualizar
     * @param usuarioActualizado los datos actualizados del usuario
     * @return el usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    public UsuarioLogin actualizarUsuario(Long id, UsuarioLogin usuarioActualizado) {
        Optional<UsuarioLogin> usuarioExistente = usuarioLoginRepository.findById(id);

        if (usuarioExistente.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        UsuarioLogin usuario = usuarioExistente.get();

        // Verificar si el nuevo username ya existe (excepto para el mismo usuario)
        if (!usuario.getUsername().equals(usuarioActualizado.getUsername())) {
            Optional<UsuarioLogin> usuarioConMismoUsername = usuarioLoginRepository.findByUsername(usuarioActualizado.getUsername());
            if (usuarioConMismoUsername.isPresent()) {
                throw new RuntimeException("El nombre de usuario ya existe: " + usuarioActualizado.getUsername());
            }
        }

        // Actualizar campos
        usuario.setUsername(usuarioActualizado.getUsername());
        usuario.setPassword(usuarioActualizado.getPassword());
        usuario.setRol(usuarioActualizado.getRol());
        if (usuarioActualizado.getPersona() != null) {
            usuario.setPersona(usuarioActualizado.getPersona());
        }

        return usuarioLoginRepository.save(usuario);
    }

    /**
     * Cambia la contraseña de un usuario
     * @param username nombre de usuario
     * @param nuevaPassword nueva contraseña
     * @return el usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    public UsuarioLogin cambiarPassword(String username, String nuevaPassword) {
        Optional<UsuarioLogin> usuarioOptional = usuarioLoginRepository.findByUsername(username);

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado: " + username);
        }

        UsuarioLogin usuario = usuarioOptional.get();
        usuario.setPassword(nuevaPassword);

        return usuarioLoginRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID
     * @param id el ID del usuario a eliminar
     * @throws RuntimeException si el usuario no existe
     */
    public void eliminarUsuario(Long id) {
        if (!usuarioLoginRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        usuarioLoginRepository.deleteById(id);
    }

    /**
     * Verifica si existe un usuario con el username dado
     * @param username nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeUsername(String username) {
        return usuarioLoginRepository.findByUsername(username).isPresent();
    }
}