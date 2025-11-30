<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Resultados de Búsqueda" />
<%@ include file="includes/header.jspf" %>

<div class="container mt-4">
    <h2 class="mb-4">Resultados de búsqueda</h2>

    <div class="row" id="contenedorProductos">
        <%@ include file="productFrag.jsp" %>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
