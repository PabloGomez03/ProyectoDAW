<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${sessionScope.user.role != 'admin-user'}">
    <c:redirect url="/" />
</c:if>

<c:set var="pageTitle" value="Nuevo Producto - Admin" />
<c:set var="activePage" value="admin" />

<%@ include file="includes/header.jspf" %>

<div class="row">
    <div class="col-md-8 offset-md-2">
        
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white p-3">
                <h3 class="mb-0">
                    <i class="bi bi-plus-circle-fill"></i> Añadir Nuevo Producto
                </h3>
            </div>
            
            <div class="card-body p-4">
                
                <form action="${pageContext.request.contextPath}/products/create" method="POST">
                    
                    <div class="mb-3">
                        <label for="name" class="form-label">Nombre del Producto</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Descripción</label>
                        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="price" class="form-label">Precio (€)</label>
                            <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="stock" class="form-label">Stock Inicial</label>
                            <input type="number" class="form-control" id="stock" name="stock" min="0" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Categoría</label>
                        <input type="text" class="form-control" id="category" name="category" placeholder="Ej: Camisetas, Pantalones..." required>
                    </div>

                    <div class="mb-3">
                        <label for="pathImage" class="form-label">Nombre de la Imagen</label>
                        <div class="input-group">
                            <span class="input-group-text">img/</span>
                            <input type="text" class="form-control" id="pathImage" name="pathImage" placeholder="ejemplo.jpg" required>
                        </div>
                        <div class="form-text text-muted">
                            <i class="bi bi-info-circle"></i> Recuerda copiar el archivo de imagen a la carpeta <strong>web/img/</strong> manualmente.
                        </div>
                    </div>

                    <hr>
                    
                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/admin" class="btn btn-secondary">
                            Cancelar
                        </a>
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-save"></i> Guardar Producto
                        </button>
                    </div>

                </form>
            </div>
        </div>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>