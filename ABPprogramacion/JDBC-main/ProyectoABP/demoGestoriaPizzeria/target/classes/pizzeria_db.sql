-- Script SQL para Base de Datos de Pizzería
-- Ejecutar en MySQL (XAMPP)

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS pizzeria_db;
USE pizzeria_db;

-- Eliminar tablas si existen (en orden correcto por dependencias)
DROP TABLE IF EXISTS pizza_ingrediente;
DROP TABLE IF EXISTS combo_producto;
DROP TABLE IF EXISTS pedido_producto;
DROP TABLE IF EXISTS bebida;
DROP TABLE IF EXISTS complemento;
DROP TABLE IF EXISTS combo;
DROP TABLE IF EXISTS pizza;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS persona;
DROP TABLE IF EXISTS direccion;
DROP TABLE IF EXISTS ingrediente;

-- Crear tabla Direccion
CREATE TABLE direccion (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           calle VARCHAR(255),
                           numero VARCHAR(10),
                           ciudad VARCHAR(100),
                           codigo_postal VARCHAR(10)
);

-- Crear tabla Persona (tabla padre)
CREATE TABLE persona (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         documento VARCHAR(50) UNIQUE,
                         email VARCHAR(255),
                         telefono VARCHAR(20),
                         direccion_id BIGINT,
                         FOREIGN KEY (direccion_id) REFERENCES direccion(id)
);

-- Crear tabla Cliente
CREATE TABLE cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         documento VARCHAR(50) UNIQUE,
                         email VARCHAR(255),
                         telefono VARCHAR(20),
                         direccion_id BIGINT,
                         FOREIGN KEY (direccion_id) REFERENCES direccion(id)
);

-- Crear tabla Empleado
CREATE TABLE empleado (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          documento VARCHAR(50) UNIQUE,
                          email VARCHAR(255),
                          telefono VARCHAR(20),
                          direccion_id BIGINT,
                          puesto ENUM('COCINERO', 'REPARTIDOR', 'CAJERO', 'GERENTE') NOT NULL,
                          sueldo DECIMAL(10,2),
                          activo BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (direccion_id) REFERENCES direccion(id)
);

-- Crear tabla Producto (tabla padre)
CREATE TABLE producto (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          precio DECIMAL(10,2) NOT NULL,
                          cantidad INT DEFAULT 1
);

-- Crear tabla Ingrediente
CREATE TABLE ingrediente (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(255) NOT NULL,
                             cantidad INT DEFAULT 0,
                             es_vegano BOOLEAN DEFAULT FALSE,
                             es_ingrediente_sin_gluten BOOLEAN DEFAULT FALSE
);

-- Crear tabla Pizza
CREATE TABLE pizza (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       precio DECIMAL(10,2) NOT NULL,
                       cantidad INT DEFAULT 1,
                       tipo ENUM('MARGARITA', 'PEPPERONI', 'HAWAIANA', 'VEGETARIANA', 'CUATRO_QUESOS', 'BARBACOA', 'CARBONARA') NOT NULL,
                       descripcion TEXT,
                       tipo_masa ENUM('FINA', 'GRUESA', 'INTEGRAL') NOT NULL,
                       es_sin_gluten BOOLEAN DEFAULT FALSE
);

-- Crear tabla Bebida
CREATE TABLE bebida (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(255) NOT NULL,
                        precio DECIMAL(10,2) NOT NULL,
                        cantidad INT DEFAULT 1,
                        tamano ENUM('PEQUEÑO', 'MEDIANO', 'GRANDE') NOT NULL,
                        disponible BOOLEAN DEFAULT TRUE
);

-- Crear tabla Complemento
CREATE TABLE complemento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(255) NOT NULL,
                             precio DECIMAL(10,2) NOT NULL,
                             cantidad INT DEFAULT 1,
                             tipo VARCHAR(100),
                             disponible BOOLEAN DEFAULT TRUE,
                             es_sin_gluten BOOLEAN DEFAULT FALSE
);

-- Crear tabla Combo
CREATE TABLE combo (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       precio DECIMAL(10,2) NOT NULL,
                       cantidad INT DEFAULT 1
);

-- Crear tabla Pedido
CREATE TABLE pedido (
                        id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
                        fecha_pedido DATETIME NOT NULL,
                        total DECIMAL(10,2),
                        estado ENUM('PENDIENTE', 'EN_PREPARACION', 'LISTO', 'ENTREGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
                        precio DECIMAL(10,2),
                        cliente_id BIGINT,
                        empleado_id BIGINT,
                        FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                        FOREIGN KEY (empleado_id) REFERENCES empleado(id)
);

-- Crear tablas de relación Many-to-Many

-- Relación Pizza-Ingrediente
CREATE TABLE pizza_ingrediente (
                                   pizza_id BIGINT,
                                   ingrediente_id BIGINT,
                                   PRIMARY KEY (pizza_id, ingrediente_id),
                                   FOREIGN KEY (pizza_id) REFERENCES pizza(id) ON DELETE CASCADE,
                                   FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id) ON DELETE CASCADE
);

-- Relación Combo-Producto
CREATE TABLE combo_producto (
                                combo_id BIGINT,
                                producto_id BIGINT,
                                PRIMARY KEY (combo_id, producto_id),
                                FOREIGN KEY (combo_id) REFERENCES combo(id) ON DELETE CASCADE,
                                FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE CASCADE
);

-- Relación Pedido-Producto
CREATE TABLE pedido_producto (
                                 pedido_id BIGINT,
                                 producto_id BIGINT,
                                 cantidad INT DEFAULT 1,
                                 PRIMARY KEY (pedido_id, producto_id),
                                 FOREIGN KEY (pedido_id) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
                                 FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE CASCADE
);

-- Insertar datos de ejemplo

-- Direcciones
INSERT INTO direccion (calle, numero, ciudad, codigo_postal) VALUES
                                                                 ('Av. Principal', '123', 'Madrid', '28001'),
                                                                 ('Calle Secundaria', '456', 'Madrid', '28002'),
                                                                 ('Plaza Mayor', '789', 'Madrid', '28003');

-- Clientes
INSERT INTO cliente (nombre, documento, email, telefono, direccion_id) VALUES
                                                                           ('Juan Pérez', '12345678A', 'juan@email.com', '666123456', 1),
                                                                           ('María García', '87654321B', 'maria@email.com', '666654321', 2),
                                                                           ('Carlos López', '11223344C', 'carlos@email.com', '666789123', 3);

-- Empleados
INSERT INTO empleado (nombre, documento, email, telefono, direccion_id, puesto, sueldo, activo) VALUES
                                                                                                    ('Ana Cocinera', '55667788D', 'ana@pizzeria.com', '666111222', 1, 'COCINERO', 1800.00, TRUE),
                                                                                                    ('Luis Repartidor', '99887766E', 'luis@pizzeria.com', '666333444', 2, 'REPARTIDOR', 1500.00, TRUE),
                                                                                                    ('Sofia Cajera', '44556677F', 'sofia@pizzeria.com', '666555666', 3, 'CAJERO', 1600.00, TRUE);

-- Ingredientes
INSERT INTO ingrediente (nombre, cantidad, es_vegano, es_ingrediente_sin_gluten) VALUES
                                                                                     ('Mozzarella', 100, FALSE, TRUE),
                                                                                     ('Tomate', 200, TRUE, TRUE),
                                                                                     ('Pepperoni', 50, FALSE, TRUE),
                                                                                     ('Champiñones', 80, TRUE, TRUE),
                                                                                     ('Jamón', 60, FALSE, TRUE),
                                                                                     ('Piña', 40, TRUE, TRUE),
                                                                                     ('Pimiento', 70, TRUE, TRUE),
                                                                                     ('Cebolla', 90, TRUE, TRUE);

-- Pizzas
INSERT INTO pizza (nombre, precio, tipo, descripcion, tipo_masa, es_sin_gluten) VALUES
                                                                                    ('Pizza Margarita', 12.50, 'MARGARITA', 'Pizza clásica con tomate y mozzarella', 'FINA', FALSE),
                                                                                    ('Pizza Pepperoni', 14.00, 'PEPPERONI', 'Pizza con pepperoni y mozzarella', 'GRUESA', FALSE),
                                                                                    ('Pizza Hawaiana', 15.50, 'HAWAIANA', 'Pizza con jamón y piña', 'FINA', FALSE),
                                                                                    ('Pizza Vegetariana', 13.50, 'VEGETARIANA', 'Pizza con verduras variadas', 'INTEGRAL', TRUE);

-- Bebidas
INSERT INTO bebida (nombre, precio, tamano, disponible) VALUES
                                                            ('Cola', 2.50, 'MEDIANO', TRUE),
                                                            ('Agua', 1.50, 'PEQUEÑO', TRUE),
                                                            ('Cerveza', 3.00, 'GRANDE', TRUE),
                                                            ('Zumo Naranja', 2.80, 'MEDIANO', TRUE);

-- Complementos
INSERT INTO complemento (nombre, precio, tipo, disponible, es_sin_gluten) VALUES
                                                                              ('Ensalada César', 6.50, 'ENSALADA', TRUE, TRUE),
                                                                              ('Patatas Fritas', 4.00, 'ACOMPAÑAMIENTO', TRUE, FALSE),
                                                                              ('Tiramisu', 5.50, 'POSTRE', TRUE, FALSE),
                                                                              ('Helado Vainilla', 4.50, 'POSTRE', TRUE, TRUE);

-- Combos
INSERT INTO combo (nombre, precio) VALUES
                                       ('Combo Familiar', 25.00),
                                       ('Combo Individual', 15.00),
                                       ('Combo Pareja', 22.00);

-- Relaciones Pizza-Ingrediente
INSERT INTO pizza_ingrediente (pizza_id, ingrediente_id) VALUES
                                                             (1, 1), (1, 2), -- Margarita: Mozzarella + Tomate
                                                             (2, 1), (2, 2), (2, 3), -- Pepperoni: Mozzarella + Tomate + Pepperoni
                                                             (3, 1), (3, 2), (3, 5), (3, 6), -- Hawaiana: Mozzarella + Tomate + Jamón + Piña
                                                             (4, 1), (4, 2), (4, 4), (4, 7), (4, 8); -- Vegetariana: Mozzarella + Tomate + Champiñones + Pimiento + Cebolla

-- Pedidos de ejemplo
INSERT INTO pedido (fecha_pedido, total, estado, precio, cliente_id, empleado_id) VALUES
                                                                                      (NOW(), 27.00, 'EN_PREPARACION', 27.00, 1, 2),
                                                                                      (NOW() - INTERVAL 1 HOUR, 19.50, 'ENTREGADO', 19.50, 2, 2),
                                                                                      (NOW() - INTERVAL 30 MINUTE, 31.00, 'LISTO', 31.00, 3, 2);

-- Mostrar estructura de tablas creadas
SHOW TABLES;