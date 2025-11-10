
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="pageTitle" value="DressStyle - El estilo es nuestra pasión" />



<%@ include file="includes/header.jspf" %>



<div class="row">
    <div class="col-12">
        <div class="p-5 mb-4 bg-light rounded-3 text-center">
            <h1 class="display-4">¡Ofertas de Temporada!</h1>
            <p class="lead">Hasta 50% de descuento en camisetas y sudaderas.</p>
        </div>
    </div>
</div>


<h2 class="my-4">Productos Destacados</h2>
        
<div class="row">
    
    
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
        <div class="card h-100">
            <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Producto 1">
            <div class="card-body">
                <h5 class="card-title">Camisa Elegante</h5>
                <p class="card-text">29.99?</p>
                <a href="#" class="btn btn-primary">Ver producto</a>
            </div>
        </div>
    </div>

    
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
        <div class="card h-100">
            <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Producto 2">
            <div class="card-body">
                <h5 class="card-title">Vaqueros Slim Fit</h5>
                <p class="card-text">45.50?</p>
                <a href="#" class="btn btn-primary">Ver producto</a>
            </div>
        </div>
    </div>

   
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
        <div class="card h-100">
            <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Producto 3">
            <div class="card-body">
                <h5 class="card-title">Zapatillas Urbanas</h5>
                <p class="card-text">79.90?</p>
                <a href="#" class="btn btn-primary">Ver producto</a>
            </div>
        </div>
    </div>

    <!-- Producto 4 (Tarjeta) -->
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
        <div class="card h-100">
            <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="Producto 4">
            <div class="card-body">
                <h5 class="card-title">Bolso de Cuero</h5>
                <p class="card-text">120.00?</p>
                <a href="#" class="btn btn-primary">Ver producto</a>
            </div>
        </div>
    </div>
    
</div>


<%@ include file="includes/footer.jspf" %>
