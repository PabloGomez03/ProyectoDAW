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


