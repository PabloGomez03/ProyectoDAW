
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="DressStyle - Inicio" />
<c:set var="activePage" value="home" />

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

    <c:forEach var="prod" items="${requestScope.list}">

        <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
            <div class="card h-100">

                <img src="${prod.pathImage}" class="card-img-top" alt="${prod.name}">

                <div class="card-body">
                    <h5 class="card-title">${prod.name}</h5>
                    <p class="card-text">${prod.price}</p>
                    <c:if test="${prod.category != 'Bolsos'}">
                    <select class="form-select mb-2" name="size">
                        <option value="S" id="s">S</option>
                        <option value="M" id ="m" selected>M</option>
                        <option value="L" id ="l">L</option>
                    </select>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/cart/add" class="btn btn-primary">Añadir al carrito</a>
                </div>
            </div>
        </div>

    </c:forEach> 

</div>
<%@ include file="includes/footer.jspf" %>
