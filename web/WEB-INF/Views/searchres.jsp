<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="Resultados de Búsqueda" />
<%@ include file="includes/header.jspf" %>

<div class="container mt-4">
    <h2 class="mb-4">Resultados de búsqueda</h2>

    <small class="text-muted fs-6">(${list != null ? list.size() : 0} productos encontrados)</small>

    <div class="row" id="contenedorProductos">
        <%@ include file="productFrag.jsp" %>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
