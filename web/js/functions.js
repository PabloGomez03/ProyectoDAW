function searchProds(event) {
    //Prevenimos que salte el evento por defecto
    if(event) event.preventDefault();
    
    //Tomamos el id
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
    } 
}

// Variables temporales para guardar qué producto estamos intentando añadir
let productoTempId = null;
let tallaTempId = null;

function gestionarCompra(idProducto, idSelectTalla) {
    
    productoTempId = idProducto;
    
    // Obtenemos la talla (si es bolso, el input hidden tiene valor "Única")
    const select = document.getElementById(idSelectTalla);
    tallaTempId = select ? select.value : "Única";

    
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
    //Rellenamos el formulario oculto del footer
    document.getElementById("hiddenId").value = productoTempId;
    document.getElementById("hiddenSize").value = tallaTempId;
    document.getElementById("hiddenOrderIdx").value = indicePedido; // Puede ser un número o vacío

    //Lo enviamos
    document.getElementById("formCarritoOculto").submit();
}

function validarPasswords() {
    
        const pass1 = document.getElementById("password").value;
        const pass2 = document.getElementById("password-confirm").value;
        
        const msgError = document.getElementById("msgError");
        const btn = document.getElementById("btnSubmit");

        
        if (pass2.length === 0) {
            msgError.classList.add("d-none");
            btn.disabled = false; 
            return;
        }

        if (pass1 === pass2) {
            // COINCIDEN
            msgError.classList.add("d-none");   // Ocultar error
            btn.disabled = false;               // Activar botón
        } else {
            // NO COINCIDEN
            msgError.classList.remove("d-none");// Mostrar error
            btn.disabled = true;                // Desactivar botón para que no pueda enviar
        }
    }

   


