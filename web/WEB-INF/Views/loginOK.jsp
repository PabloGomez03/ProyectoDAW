
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="pageTitle" value="¡Bienvenido!" />

<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-8 offset-md-2 text-center">
        
        <div class="alert alert-success p-5 shadow-sm">
            
            <i class="bi bi-check-circle-fill" style="font-size: 3rem; color: var(--bs-success);"></i>
            
            <h2 class="mt-3">¡Inicio de sesión correcto!</h2>
            
            <p class="lead">
                Bienvenido de nuevo, <strong><c:out value="${sessionScope.usuarioLogueado.name}"/></strong>.
            </p>
            
            <hr>
            
            <a href="/daw" class="btn btn-success">
                Ir a la tienda
            </a>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
