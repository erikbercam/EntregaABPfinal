🍕 ABP - Gestoría de Pizzería
Integrantes del equipo
Erik Bernabé
(Puedes añadir más nombres si hay otros integrantes)

Descripción
Este proyecto es una API RESTful desarrollada en Java con Spring Boot, destinada a la gestión integral de una pizzería. Permite manejar información sobre clientes, empleados, pedidos, pizzas, ingredientes, productos, bebidas y direcciones.

Está diseñada para facilitar el trabajo de gestión mediante el uso de endpoints organizados por entidad, con operaciones básicas como obtener datos (GET) y registrar nuevos (POST).

La estructura del proyecto sigue el patrón MVC, con paquetes dedicados a controladores, servicios, repositorios, modelos y DTOs, organizados para un mantenimiento y escalabilidad óptimos.

Endpoints disponibles
Los endpoints disponibles actualmente son los siguientes, todos accesibles bajo el prefijo /api:

Direcciones
GET /api/direcciones — Obtener todas las direcciones

POST /api/direcciones — Crear una nueva dirección

Clientes
GET /api/clientes — Obtener todos los clientes

POST /api/clientes — Registrar un nuevo cliente

Ingredientes
GET /api/ingredientes — Obtener todos los ingredientes

POST /api/ingredientes — Agregar un nuevo ingrediente

Pedidos
GET /api/pedidos — Listar todos los pedidos

POST /api/pedidos — Crear un nuevo pedido

Pizzas
GET /api/pizzas — Ver todas las pizzas

POST /api/pizzas — Crear una nueva pizza

Empleados
GET /api/empleados — Obtener todos los empleados

POST /api/empleados — Registrar un nuevo empleado
