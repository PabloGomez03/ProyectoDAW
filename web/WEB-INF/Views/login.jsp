
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Iniciar Sesión" />

<%@ include file="includes/header.jspf" %>


<div class="row">
    <div class="col-md-6 offset-md-3">
        
        <div class="card shadow-sm p-4">
            <h2 class="text-center mb-4">Iniciar Sesión</h2>
            
            <form action="/daw/login" method="POST">
                
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                
                <button type="submit" class="btn btn-primary w-100">Entrar</button>

                <div class="text-center mt-3">
                    <p>¿No tienes cuenta? <a href="/daw/signup">Regístrate aquí</a></p>
                </div>
            </form>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
