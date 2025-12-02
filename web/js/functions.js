function searchProds(event) {
    
    if(event) event.preventDefault();
    
    const input = document.getElementById("searchInput");
    const texto = input.value;
    const contextPath = "/daw"; 
    
    
    const contenedor = document.getElementById("contenedorProductos");

    if (contenedor) {
        

        fetch(contextPath + '/products/searchAjax?q=' + encodeURIComponent(texto))
            .then(r => r.text())
            .then(html => {
                contenedor.innerHTML = html; 
            });
    } else {
        
        window.location.href = contextPath + '/products/search?q=' + encodeURIComponent(texto);
    }
}

// Variables temporales para guardar qué producto estamos intentando añadir
let productoTempId = null;
let tallaTempId = null;

function gestionarCompra(idProducto, idSelectTalla) {
    // 1. Capturamos los datos del producto
    productoTempId = idProducto;
    
    // Obtenemos la talla (si es bolso, el input hidden tiene valor "Única")
    const select = document.getElementById(idSelectTalla);
    tallaTempId = select ? select.value : "Única";

    // 2. DECISIÓN
    if (numPedidosUsuario > 1) {
        // Si hay más de 1 pedido -> Abrimos el Modal
        const modal = new bootstrap.Modal(document.getElementById('modalSeleccionPedido'));
        modal.show();
    } else {
        // Si hay 0 o 1 pedido -> Enviamos directo (índice vacío)
        enviarAlCarrito('');
    }
}

function enviarAlCarrito(indicePedido) {
    // 1. Rellenamos el formulario oculto del footer
    document.getElementById("hiddenId").value = productoTempId;
    document.getElementById("hiddenSize").value = tallaTempId;
    document.getElementById("hiddenOrderIdx").value = indicePedido; // Puede ser un número o vacío

    // 2. Lo enviamos
    document.getElementById("formCarritoOculto").submit();
}


