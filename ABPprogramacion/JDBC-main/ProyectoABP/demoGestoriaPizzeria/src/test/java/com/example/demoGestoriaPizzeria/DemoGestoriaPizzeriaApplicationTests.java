package com.example.demoGestoriaPizzeria;

import com.example.demoGestoriaPizzeria.Enums.*;
import com.example.demoGestoriaPizzeria.Model.*;
import com.example.demoGestoriaPizzeria.Repository.*;
import com.example.demoGestoriaPizzeria.Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoGestoriaPizzeriaApplicationTests {

	// =============== MOCKS DE REPOSITORIOS ===============
	@Mock
	private BebidaRepository bebidaRepository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private DireccionRepository direccionRepository;
	@Mock
	private EmpleadoRepository empleadoRepository;
	@Mock
	private IngredienteRepository ingredienteRepository;
	@Mock
	private PedidoRepository pedidoRepository;
	@Mock
	private PizzaRepository pizzaRepository;
	@Mock
	private ProductoRepository productoRepository;
	@Mock
	private UsuarioLoginRepository usuarioLoginRepository;

	// =============== SERVICES A TESTEAR ===============
	@InjectMocks
	private BebidaService bebidaService;
	@InjectMocks
	private ClienteService clienteService;
	@InjectMocks
	private DireccionService direccionService;
	@InjectMocks
	private EmpleadoService empleadoService;
	@InjectMocks
	private IngredienteService ingredienteService;
	@InjectMocks
	private PedidoService pedidoService;
	@InjectMocks
	private PizzaService pizzaService;
	@InjectMocks
	private UsuarioLoginService usuarioLoginService;

	// =============== DATOS DE PRUEBA ===============
	private Bebida bebida;
	private Cliente cliente;
	private Direccion direccion;
	private Empleado empleado;
	private Ingrediente ingrediente;
	private Pedido pedido;
	private Pizza pizza;
	private UsuarioLogin usuarioLogin;

	@BeforeEach
	void setUp() {
		// Setup de dirección
		direccion = new Direccion();
		direccion.setId(1L);
		direccion.setCalle("Calle Principal");
		direccion.setNumero("123");
		direccion.setCiudad("Madrid");
		direccion.setCodigoPostal("28001");

		// Setup de bebida
		bebida = new Bebida("Coca Cola", 2.50, TamanoBebida.MEDIANO);
		bebida.setId(1L);
		bebida.setCantidad(50);
		bebida.setDisponible(true);

		// Setup de cliente
		cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNombre("Juan Pérez");
		cliente.setDocumento("12345678A");
		cliente.setEmail("juan@email.com");
		cliente.setTelefono("666123456");
		cliente.setDireccion(direccion);

		// Setup de ingrediente
		ingrediente = new Ingrediente();
		ingrediente.setId(1L);
		ingrediente.setNombre("Queso Mozzarella");
		ingrediente.setCantidad(100);
		ingrediente.setEsVegano(false);
		ingrediente.setEsIngredienteSinGluten(true);

		// Setup de pizza
		pizza = new Pizza();
		pizza.setId(1L);
		pizza.setNombre("Margarita");
		pizza.setPrecio(12.50);
		pizza.setCantidad(20);
		pizza.setDescripcion("Pizza clásica italiana");
		pizza.setTipo(enumPizza.MARGARITA);
		pizza.setTipoMasa(enumMassa.FINA);
		pizza.setEsSinGluten(false);
		pizza.setIngredientes(new HashSet<>(Arrays.asList(ingrediente)));

		// Setup de empleado
		empleado = new Empleado();
		empleado.setId(1L);
		empleado.setNombre("María García");
		empleado.setDocumento("87654321B");
		empleado.setEmail("maria@pizzeria.com");
		empleado.setTelefono("666987654");
		empleado.setDireccion(direccion);
		empleado.setPuesto(enumPuestoTrabajador.REPARTIDOR);
		empleado.setSueldo(1500.0);
		empleado.setActivo(true);

		// Setup de pedido
		pedido = new Pedido();
		pedido.setIdPedido(1L);
		pedido.setCliente(cliente);
		pedido.setRepartidor(empleado);
		pedido.setEstado(enumEstadoPedido.PENDIENTE);
		pedido.setFechaPedido(LocalDateTime.now());
		pedido.setPrecio(15.0);
		pedido.setTotal(15.0);
		pedido.setProductos(new HashSet<>(Arrays.asList(pizza, bebida)));

		// Setup de usuario login
		usuarioLogin = new UsuarioLogin();
		usuarioLogin.setId(1L);
		usuarioLogin.setUsername("juanp");
		usuarioLogin.setPassword("1234");
		usuarioLogin.setRol(enumRol.CLIENTE);
		usuarioLogin.setPersona(cliente);
	}

	// =============== TESTS DE BEBIDA SERVICE ===============

	@Test
	void testGuardarBebida() {
		when(bebidaRepository.findByNombreAndTamano(anyString(), any(TamanoBebida.class)))
				.thenReturn(Optional.empty());
		when(bebidaRepository.save(any(Bebida.class))).thenReturn(bebida);

		Bebida resultado = bebidaService.guardarBebida(bebida);

		assertNotNull(resultado);
		assertEquals("Coca Cola", resultado.getNombre());
		assertEquals(TamanoBebida.MEDIANO, resultado.getTamano());
		verify(bebidaRepository).save(bebida);
	}

	@Test
	void testGuardarBebidaDuplicada() {
		when(bebidaRepository.findByNombreAndTamano(anyString(), any(TamanoBebida.class)))
				.thenReturn(Optional.of(bebida));

		assertThrows(RuntimeException.class, () -> bebidaService.guardarBebida(bebida));
		verify(bebidaRepository, never()).save(any());
	}

	@Test
	void testObtenerBebidasDisponibles() {
		when(bebidaRepository.findByDisponibleTrue()).thenReturn(Arrays.asList(bebida));

		List<Bebida> resultado = bebidaService.obtenerBebidasDisponibles();

		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).isDisponible());
		verify(bebidaRepository).findByDisponibleTrue();
	}

	@Test
	void testObtenerBebidasEnStock() {
		when(bebidaRepository.findByDisponibleTrueAndCantidadGreaterThan(0))
				.thenReturn(Arrays.asList(bebida));

		List<Bebida> resultado = bebidaService.obtenerBebidasEnStock();

		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).getCantidad() > 0);
		verify(bebidaRepository).findByDisponibleTrueAndCantidadGreaterThan(0);
	}

	// =============== TESTS DE CLIENTE SERVICE ===============

	@Test
	void testGuardarCliente() {
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

		Cliente resultado = clienteService.guardarCliente(cliente);

		assertNotNull(resultado);
		assertEquals("Juan Pérez", resultado.getNombre());
		assertEquals("12345678A", resultado.getDocumento());
		verify(clienteRepository).save(cliente);
	}

	@Test
	void testBuscarClientePorDocumento() {
		when(clienteRepository.findByDocumento("12345678A")).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = clienteService.buscarPorDocumento("12345678A");

		assertTrue(resultado.isPresent());
		assertEquals("12345678A", resultado.get().getDocumento());
		verify(clienteRepository).findByDocumento("12345678A");
	}

	// =============== TESTS DE PIZZA SERVICE ===============

	@Test
	void testGuardarPizza() {
		when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);

		Pizza resultado = pizzaService.guardarPizza(pizza);

		assertNotNull(resultado);
		assertEquals("Margarita", resultado.getNombre());
		assertEquals(enumPizza.MARGARITA, resultado.getTipo());
		verify(pizzaRepository).save(pizza);
	}

	@Test
	void testBuscarPizzasPorTipo() {
		when(pizzaRepository.findByTipo(enumPizza.MARGARITA)).thenReturn(Arrays.asList(pizza));

		List<Pizza> resultado = pizzaService.buscarPizzasPorTipo(enumPizza.MARGARITA);

		assertEquals(1, resultado.size());
		assertEquals(enumPizza.MARGARITA, resultado.get(0).getTipo());
		verify(pizzaRepository).findByTipo(enumPizza.MARGARITA);
	}

	@Test
	void testBuscarPizzasSinGluten() {
		Pizza pizzaSinGluten = new Pizza();
		pizzaSinGluten.setEsSinGluten(true);
		pizzaSinGluten.setNombre("Margarita Sin Gluten");

		when(pizzaRepository.findByEsSinGlutenTrue()).thenReturn(Arrays.asList(pizzaSinGluten));

		List<Pizza> resultado = pizzaService.buscarPizzasSinGluten();

		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).isEsSinGluten());
		verify(pizzaRepository).findByEsSinGlutenTrue();
	}

	// =============== TESTS DE INGREDIENTE SERVICE ===============

	@Test
	void testGuardarIngrediente() {
		when(ingredienteRepository.save(any(Ingrediente.class))).thenReturn(ingrediente);

		Ingrediente resultado = ingredienteService.guardarIngrediente(ingrediente);

		assertNotNull(resultado);
		assertEquals("Queso Mozzarella", resultado.getNombre());
		assertEquals(100, resultado.getCantidad());
		verify(ingredienteRepository).save(ingrediente);
	}

	@Test
	void testObtenerIngredientesVeganos() {
		Ingrediente ingredienteVegano = new Ingrediente();
		ingredienteVegano.setNombre("Tomate");
		ingredienteVegano.setEsVegano(true);

		when(ingredienteRepository.findByEsVeganoTrue()).thenReturn(Arrays.asList(ingredienteVegano));

		List<Ingrediente> resultado = ingredienteService.obtenerIngredientesVeganos();

		assertEquals(1, resultado.size());
		assertTrue(resultado.get(0).isEsVegano());
		verify(ingredienteRepository).findByEsVeganoTrue();
	}

	// =============== TESTS DE EMPLEADO SERVICE ===============

	@Test
	void testGuardarEmpleado() {
		when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

		Empleado resultado = empleadoService.guardarEmpleado(empleado);

		assertNotNull(resultado);
		assertEquals("María García", resultado.getNombre());
		assertEquals(enumPuestoTrabajador.REPARTIDOR, resultado.getPuesto());
		verify(empleadoRepository).save(empleado);
	}

	@Test
	void testObtenerEmpleadosPorPuesto() {
		when(empleadoRepository.findByPuesto(enumPuestoTrabajador.REPARTIDOR))
				.thenReturn(Arrays.asList(empleado));

		List<Empleado> resultado = empleadoService.obtenerEmpleadosPorPuesto(enumPuestoTrabajador.REPARTIDOR);

		assertEquals(1, resultado.size());
		assertEquals(enumPuestoTrabajador.REPARTIDOR, resultado.get(0).getPuesto());
		verify(empleadoRepository).findByPuesto(enumPuestoTrabajador.REPARTIDOR);
	}

	// =============== TESTS DE PEDIDO SERVICE ===============

	@Test
	void testGuardarPedido() {
		Pedido nuevoPedido = new Pedido();
		nuevoPedido.setCliente(cliente);
		nuevoPedido.setProductos(new HashSet<>(Arrays.asList(pizza, bebida)));
		nuevoPedido.setTotal(15.0);

		when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

		Pedido resultado = pedidoService.guardarPedido(nuevoPedido);

		assertNotNull(resultado);
		assertNotNull(resultado.getFechaPedido());
		assertEquals(enumEstadoPedido.PENDIENTE, resultado.getEstado());
		verify(pedidoRepository).save(any(Pedido.class));
	}

	@Test
	void testCambiarEstadoPedido() {
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

		Pedido pedidoActualizado = new Pedido();
		pedidoActualizado.setIdPedido(1L);
		pedidoActualizado.setEstado(enumEstadoPedido.EN_PREPARACION);

		when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoActualizado);

		Pedido resultado = pedidoService.cambiarEstadoPedido(1L, enumEstadoPedido.EN_PREPARACION);

		assertEquals(enumEstadoPedido.EN_PREPARACION, resultado.getEstado());
		verify(pedidoRepository).findById(1L);
		verify(pedidoRepository).save(any(Pedido.class));
	}

	@Test
	void testObtenerPedidosPorEstado() {
		when(pedidoRepository.findByEstado(enumEstadoPedido.PENDIENTE))
				.thenReturn(Arrays.asList(pedido));

		List<Pedido> resultado = pedidoService.obtenerPedidosPorEstado(enumEstadoPedido.PENDIENTE);

		assertEquals(1, resultado.size());
		assertEquals(enumEstadoPedido.PENDIENTE, resultado.get(0).getEstado());
		verify(pedidoRepository).findByEstado(enumEstadoPedido.PENDIENTE);
	}

	// =============== TESTS DE DIRECCIÓN SERVICE ===============

	@Test
	void testGuardarDireccion() {
		when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

		Direccion resultado = direccionService.guardarDireccion(direccion);

		assertNotNull(resultado);
		assertEquals("Calle Principal", resultado.getCalle());
		assertEquals("Madrid", resultado.getCiudad());
		verify(direccionRepository).save(direccion);
	}

	@Test
	void testBuscarDireccionesPorCiudad() {
		when(direccionRepository.findByCiudad("Madrid")).thenReturn(Arrays.asList(direccion));

		List<Direccion> resultado = direccionService.buscarDireccionesPorCiudad("Madrid");

		assertEquals(1, resultado.size());
		assertEquals("Madrid", resultado.get(0).getCiudad());
		verify(direccionRepository).findByCiudad("Madrid");
	}

	// =============== TESTS DE USUARIO LOGIN SERVICE ===============

	@Test
	void testGuardarUsuarioLogin() {
		when(usuarioLoginRepository.save(any(UsuarioLogin.class))).thenReturn(usuarioLogin);

		UsuarioLogin resultado = usuarioLoginService.guardarUsuario(usuarioLogin);

		assertNotNull(resultado);
		assertEquals("juanp", resultado.getUsername());
		assertEquals(enumRol.CLIENTE, resultado.getRol());
		verify(usuarioLoginRepository).save(usuarioLogin);
	}

	@Test
	void testBuscarUsuarioPorUsername() {
		when(usuarioLoginRepository.findByUsername("juanp")).thenReturn(Optional.of(usuarioLogin));

		Optional<UsuarioLogin> resultado = usuarioLoginService.buscarPorUsername("juanp");

		assertTrue(resultado.isPresent());
		assertEquals("juanp", resultado.get().getUsername());
		verify(usuarioLoginRepository).findByUsername("juanp");
	}

	// =============== TESTS DE CASOS EDGE Y EXCEPCIONES ===============

	@Test
	void testBuscarPedidoInexistente() {
		when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			pedidoService.actualizarPedido(999L, new Pedido());
		});

		verify(pedidoRepository).findById(999L);
	}

	@Test
	void testActualizarPizzaInexistente() {
		when(pizzaRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			pizzaService.actualizarPizza(999L, new Pizza());
		});

		verify(pizzaRepository).findById(999L);
	}

	@Test
	void testEliminarBebidaInexistente() {
		when(bebidaRepository.existsById(999L)).thenReturn(false);

		assertThrows(RuntimeException.class, () -> {
			bebidaService.eliminarBebida(999L);
		});

		verify(bebidaRepository).existsById(999L);
		verify(bebidaRepository, never()).deleteById(anyLong());
	}

	@Test
	void testAutenticarUsuarioConCredencialesValidas() {
		when(usuarioLoginRepository.findByUsernameAndPassword("juanp", "1234"))
				.thenReturn(Optional.of(usuarioLogin));

		Optional<UsuarioLogin> resultado = usuarioLoginService.autenticarUsuario("juanp", "1234");

		assertTrue(resultado.isPresent());
		assertEquals("juanp", resultado.get().getUsername());
	}
}