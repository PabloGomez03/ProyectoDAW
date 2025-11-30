<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty list}">
    <div class="col-12 text-center mt-5">
        <p class="text-muted">No se encontraron productos.</p>
    </div>
</c:if>


<c:forEach var="p" items="${requestScope.list}">
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4 fade-in">
        <div class="card h-100 shadow-sm">
            <img src="${pageContext.request.contextPath}/${p.pathImage}" 
                 class="card-img-top" alt="${p.name}"
                 style="height: 200px; object-fit: cover;">
            
            <div class="card-body d-flex flex-column">
                <h5 class="card-title">${p.name}</h5>
                <p class="card-text text-primary fw-bold">${p.price}?</p>
                
                <div class="mt-auto">
                    <a href="#" class="btn btn-primary w-100">Añadir al Carrito</a>
                </div>
            </div>
        </div>
    </div>
</c:forEach>