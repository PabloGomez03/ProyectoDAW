
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${sessionScope.user}" />
<c:set var="pageTitle" value="Perfil de ${user.name}" />




<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-8 offset-md-2">
        
        <div class="card shadow-sm">
            <div class="card-header p-3">
                <h2 class="mb-0">
                    <i class="bi bi-person-circle"></i>
                    Mi Perfil
                </h2>
            </div>
            <div class="card-body p-4">
                
                <form action="${pageContext.request.contextPath}/modify" method="POST">
                    
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="userId" value="${user.id}">

                    <div class="mb-3">
                        <label for="name" class="form-label">Nombre Completo</label>
                        <input type="text" class="form-control" id="name" name="name" 
                               value="<c:out value='${user.name}'/>" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" 
                               value="<c:out value='${user.email}'/>" required>
                    </div>

                    <div class="mb-3">
                        <label for="address" class="form-label">Dirección</label>
                        <input type="text" class="form-control" id="address" name="address" 
                               value="<c:out value='${user.address}'/>" required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Nueva Contraseña (Opcional)</label>
                        <input type="password" class="form-control" id="password" name="password" 
                               placeholder="Dejar en blanco para no cambiar">
                        <div class="form-text">
                            Introduce una nueva contraseña solo si deseas cambiarla.
                        </div>
                    </div>
                    
                    <hr>
                    
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-save-fill"></i> Guardar Cambios
                    </button>
                    
                </form>
            </div>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
