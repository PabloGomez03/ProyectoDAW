
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Registro" />


<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-6 offset-md-3">
        
        <div class="card shadow-sm p-4">
            <h2 class="text-center mb-4">Crear una Cuenta</h2>
            
            <c:if test="${not empty requestScope.login}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i> 
                    <c:out value="${requestScope.login}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/signup/save" method="POST">
                
                <div class="mb-3">
                    <label for="name" class="form-label">Nombre Completo</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>
                
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Dirección</label>
                    <input type="text" class="form-control" id="address" name="address" required>
                </div>
                
                <button type="submit" class="btn btn-primary w-100">Crear Cuenta</button>

                <div class="text-center mt-3">
                    <p>¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión</a></p>
                </div>
            </form>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
