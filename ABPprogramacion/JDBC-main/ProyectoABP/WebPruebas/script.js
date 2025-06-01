document.addEventListener('DOMContentLoaded', function () {
    // Tabs
    const tabBtns = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const tabId = btn.getAttribute('data-tab');
            tabBtns.forEach(b => b.classList.remove('active'));
            tabContents.forEach(c => c.classList.remove('active'));
            btn.classList.add('active');
            document.getElementById(tabId).classList.add('active');
        });
    });

    // Base URL
    const baseUrlInput = document.getElementById('base-url');
    const saveUrlBtn = document.getElementById('save-url');

    if (localStorage.getItem('apiBaseUrl')) {
        baseUrlInput.value = localStorage.getItem('apiBaseUrl');
    }

    saveUrlBtn.addEventListener('click', () => {
        localStorage.setItem('apiBaseUrl', baseUrlInput.value);
        alert('URL base guardada correctamente');
    });

    // Try buttons
    const tryBtns = document.querySelectorAll('.try-btn');

    tryBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            const endpointType = this.getAttribute('data-endpoint');
            const container = this.closest('.endpoint');
            const responseDiv = container.querySelector('.response');
            const params = container.querySelectorAll('.param');
            const bodyTextarea = container.querySelector('.request-body');

            responseDiv.textContent = 'Enviando solicitud...';

            let baseUrl = baseUrlInput.value.replace(/\/+$/, '');
            let url = '';
            let method = 'GET';
            let body = null;

            switch (endpointType) {
                case 'clientes':
                    url = `${baseUrl}/clientes`;
                    break;
                case 'clientes-documento':
                    url = `${baseUrl}/clientes/documento/${params[0].value}`;
                    break;
                case 'clientes-create':
                    url = `${baseUrl}/clientes`;
                    method = 'POST';
                    break;
                case 'pedidos-estado':
                    url = `${baseUrl}/pedidos/estado/${params[0].value}`;
                    break;
                case 'pedidos-create':
                    url = `${baseUrl}/pedidos`;
                    method = 'POST';
                    break;
                case 'pedidos-change-status':
                    url = `${baseUrl}/pedidos/${params[0].value}/estado`;
                    method = 'PATCH';
                    break;
                case 'pizzas-sin-gluten':
                    url = `${baseUrl}/pizzas/sin-gluten`;
                    break;
                case 'pizzas-tipo':
                    url = `${baseUrl}/pizzas/tipo/${params[0].value}`;
                    break;
                case 'pizzas-create':
                    url = `${baseUrl}/pizzas`;
                    method = 'POST';
                    break;
                case 'ingredientes-veganos':
                    url = `${baseUrl}/ingredientes`;
                    break;
                case 'empleados-puesto':
                    url = `${baseUrl}/empleados/puesto/${params[0].value}`;
                    break;
                case 'direcciones-ciudad':
                    url = `${baseUrl}/direcciones${params[0].value}`;
                    break;
                case 'direcciones-create':
                    url = `${baseUrl}/direcciones`;
                    method = 'POST';
                    break;
                default:
                    responseDiv.textContent = '⚠️ Endpoint no reconocido';
                    return;
            }

            if (bodyTextarea && (method === 'POST' || method === 'PATCH')) {
                try {
                    body = JSON.stringify(JSON.parse(bodyTextarea.value));
                } catch (e) {
                    responseDiv.textContent = '❌ Error en el JSON del cuerpo de la petición.';
                    return;
                }
            }

            fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                },
                body: body
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => {
                            throw new Error(JSON.stringify(err));
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    responseDiv.textContent = JSON.stringify(data, null, 2);
                })
                .catch(error => {
                    responseDiv.textContent = `❌ Error: ${error.message}`;
                });
        });
    });

    // Prefilled request bodies
    document.querySelectorAll('.request-body').forEach(textarea => {
        if (!textarea.value.trim()) {
            const h3Text = textarea.closest('.endpoint').querySelector('h3')?.textContent || '';

            if (h3Text.includes('cliente')) {
                textarea.value = JSON.stringify({
                    nombre: "Juan Pérez",
                    documento: "12345678A",
                    email: "juan@email.com",
                    telefono: "666123456",
                    direccion: {
                        calle: "Calle Principal",
                        numero: "123",
                        ciudad: "Madrid",
                        codigoPostal: "28001"
                    }
                }, null, 2);
            } else if (h3Text.includes('pedido') && !h3Text.includes('estado')) {
                textarea.value = JSON.stringify({
                    fechaPedido: "2025-06-01T17:00:00",
                    total: 25.5,
                    estado: "PENDIENTE",
                    clienteId: 1,
                    repartidorId: 2,
                    precio: 25.5,
                    productoIds: [1, 2]
                }, null, 2);
            } else if (h3Text.includes('estado de pedido')) {
                textarea.value = JSON.stringify({
                    estado: "EN_PREPARACION"
                }, null, 2);
            } else if (h3Text.includes('pizza')) {
                textarea.value = JSON.stringify({
                    nombre: "PEPPERONI",
                    descripcion: "Pizza clásica con tomate y mozzarella",
                    tipo: "PEPPERONI",
                    tipoMasa: "INTEGRAL",
                    esSinGluten: false,
                    precio: 8.5,
                    cantidad: 10,
                    ingredientes: [1, 2]
                }, null, 2);
            } else if (h3Text.includes('dirección') && h3Text.includes('nueva')) {
                textarea.value = JSON.stringify({
                    calle: "Gran Vía",
                    numero: "123",
                    ciudad: "Madrid",
                    codigoPostal: "28013"
                }, null, 2);
            }
        }
    });
});
