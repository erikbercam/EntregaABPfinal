
🍕 ABP - Gestoría de Pizzería
API RESTful desarrollada en Java + Spring Boot para la gestión integral de una pizzería.

👥 Integrantes del Equipo

👨‍💻 Erik Bernabé

👨‍💻 Joel Vila

👨‍💻 Sebastián Ordoñez

👨‍💻 Martí López

📌 Descripción del Proyecto

Una solución completa para administrar una pizzería, con endpoints que permiten gestionar:

📦 Pedidos

👨‍🍳 Empleados

🧑 Clientes

🍕 Pizzas

🧂 Ingredientes

🥤 Bebidas

🏠 Direcciones

🔧 Construida siguiendo el patrón MVC, con paquetes organizados en:

📁 controllers/
📁 services/
📁 repositories/
📁 models/
📁 dtos/

Diseñada para ser escalable, mantenible y de fácil integración.

🌐 Endpoints Disponibles

Todos los endpoints están bajo el prefijo:
/api

🏠 Direcciones
GET /api/direcciones → Obtener todas las direcciones

POST /api/direcciones → Crear una nueva dirección

🧑 Clientes
GET /api/clientes → Obtener todos los clientes

POST /api/clientes → Registrar un nuevo cliente

🧂 Ingredientes
GET /api/ingredientes → Obtener todos los ingredientes

POST /api/ingredientes → Agregar un nuevo ingrediente

📦 Pedidos
GET /api/pedidos → Listar todos los pedidos

POST /api/pedidos → Crear un nuevo pedido

🍕 Pizzas
GET /api/pizzas → Ver todas las pizzas

POST /api/pizzas → Crear una nueva pizza

👨‍🍳 Empleados
GET /api/empleados → Obtener todos los empleados

POST /api/empleados → Registrar un nuevo empleado

🛠️ Tecnologías Usadas

☕ Java 22

🌱 Spring Boot

🌐 REST API

🐘MySQL

🔁 Spring Data JPA



🚀 Cómo Ejecutar el Proyecto
Clona el repositorio

git clone https://github.com/tu_usuario/gestoria-pizzeria.git
Importa en tu IDE favorito (IntelliJ / Eclipse)

Configura tu application.properties o application.yml con tus datos de BD

Ejecuta la clase principal:

GestoriaPizzeriaApplication.java

Accede a los endpoints en:
http://localhost:8080/api/
