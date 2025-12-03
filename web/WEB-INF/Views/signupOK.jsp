<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="pageTitle" value="Registro Completo - DressStyle" />



<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-8 offset-md-2 text-center">
        
        <div class="alert alert-success p-5 shadow-sm">
            
            <i class="bi bi-check-circle-fill" style="font-size: 3rem; color: var(--bs-success);"></i>
            
            <h2 class="mt-3">¡Registro completado!</h2>
            
            <p class="lead">
                Tu cuenta ha sido creada con éxito.
            </p>
            
            <hr>
            
            <a href="${pageContext.request.contextPath}" class="btn btn-primary">
                Volver
            </a>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
