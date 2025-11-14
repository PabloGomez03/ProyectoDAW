
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="pageTitle" value="Modificacion" />

<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-8 offset-md-2 text-center">
        
        <div class="alert alert-success p-5 shadow-sm">
            
            <i class="bi bi-check-circle-fill" style="font-size: 3rem; color: var(--bs-success);"></i>
            
            <h2 class="mt-3">¡Modificacion Correcta!</h2>
            
            <hr>
            
            <a href="${pageContext.request.contextPath}/profile/${sessionScope.user.id}" class="btn btn-success">
                Ir al perfil
            </a>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
