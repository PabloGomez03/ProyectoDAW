function buscarProductos(event) {
    // 1. Evitamos que el formulario se envíe solo
    if(event) event.preventDefault();
    
    const input = document.getElementById("searchInput");
    const texto = input.value;
    const contextPath = "/daw"; // Asegúrate que coincide con tu proyecto
    
    // 2. Buscamos si estamos en la página de resultados
    const contenedor = document.getElementById("contenedorProductos");

    if (contenedor) {
        // ESTAMOS EN RESULTADOS -> USAMOS AJAX
        // Llamamos a /searchAjax para pedir solo el HTML de las tarjetas
        fetch(contextPath + '/products/searchAjax?q=' + encodeURIComponent(texto))
            .then(r => r.text())
            .then(html => {
                contenedor.innerHTML = html; // Reemplazamos solo las tarjetas
            });
    } else {
        // ESTAMOS EN OTRA PÁGINA (Index) -> REDIRIGIMOS
        // Vamos a la página normal de búsqueda
        window.location.href = contextPath + '/products/search?q=' + encodeURIComponent(texto);
    }
}


