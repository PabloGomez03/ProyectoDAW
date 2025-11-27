
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${sessionScope.user.role != 'admin-user'}">
    <c:redirect url="/" />
</c:if>

<c:set var="pageTitle" value="Gestión - Admin" />
<c:set var="users" value="${requestScope.ulist}" />
<c:set var="products" value="${requestScope.plist}" />

<%@ include file="includes/header.jspf" %>

<div class="row mb-4">
    <div class="col-12">
        <h1 class="border-bottom pb-2">
            <i class="bi bi-speedometer2"></i> Panel de Administración
        </h1>
    </div>
</div>

<div class="row mb-4">
    <div class="col-md-6">
        <div class="card text-white bg-primary mb-3 shadow-sm">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <h5 class="card-title">Productos</h5>
                    <h2 class="mb-0">${products.size()}</h2>
                </div>
                <i class="bi bi-tags-fill" style="font-size: 3rem; opacity: 0.5;"></i>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card text-white bg-success mb-3 shadow-sm">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <h5 class="card-title">Usuarios Registrados</h5>
                    <h2 class="mb-0">${users.size()}</h2>
                </div>
                <i class="bi bi-people-fill" style="font-size: 3rem; opacity: 0.5;"></i>
            </div>
        </div>
    </div>
</div>

<div class="card shadow-sm">
    <div class="card-header">
        <ul class="nav nav-tabs card-header-tabs" id="adminTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="products-tab" data-bs-toggle="tab" data-bs-target="#products" type="button" role="tab">
                    <i class="bi bi-box-seam"></i> Productos
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="users-tab" data-bs-toggle="tab" data-bs-target="#users" type="button" role="tab">
                    <i class="bi bi-person-lines-fill"></i> Usuarios
                </button>
            </li>
        </ul>
    </div>

    <div class="card-body">
        <div class="tab-content" id="adminTabsContent">

            <div class="tab-pane fade show active" id="products" role="tabpanel">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>Inventario</h4>
                    <a href="${pageContext.request.contextPath}/products/new" class="btn btn-primary btn-sm">
                        <i class="bi bi-plus-circle"></i> Nuevo Producto
                    </a>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Img</th>
                                <th>Nombre</th>
                                <th>Categoría</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th class="text-end">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${products}">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}/${p.pathImage}" 
                                             alt="img" style="width: 40px; height: 40px; object-fit: cover;" class="rounded">
                                    </td>
                                    <td class="fw-bold">${p.name}</td>
                                    <td><span class="badge bg-secondary">${p.category}</span></td>
                                    <td>${p.price}?</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.stock < 5}"><span class="text-danger fw-bold">${p.stock} (Bajo)</span></c:when>
                                            <c:otherwise><span class="text-success">${p.stock}</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end">
                                        <a href="${pageContext.request.contextPath}/products/delete/${p.id}" class="btn btn-outline-danger btn-sm" title="Eliminar" onclick="return confirm('Desea eliminar el producto?')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="tab-pane fade" id="users" role="tabpanel">
                <h4 class="mb-3">Listado de Clientes</h4>
                <div class="table-responsive">
                    <table class="table table-striped align-middle">
                        <thead class="table-secondary">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Dirección</th>
                                <th>Rol</th>
                                <th class="text-end">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#${u.id}</td>
                                    <td>${u.name}</td>
                                    <td>${u.email}</td>
                                    <td class="text-truncate" style="max-width: 150px;">${u.address}</td>
                                    <td>
                                        <c:if test="${u.role == 'admin-user'}">
                                            <span class="badge bg-warning text-dark">Admin</span>
                                        </c:if>
                                        <c:if test="${u.role != 'admin-user'}">
                                            <span class="badge bg-info text-dark">User</span>
                                        </c:if>
                                    </td>
                                    <td class="text-end">
                                        <c:choose>
                                            <c:when test ="${u.role == 'admin-user'}">
                                                <a href="#" class="btn btn-outline-danger btn-sm" title="Eliminar Usuario" onclick="return alert('No se puede eliminar al administrador');return false;">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/remove/${u.id}" class="btn btn-outline-danger btn-sm" title="Eliminar Usuario" onclick="return confirm('Desea eliminar el usuario?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </c:otherwise>

                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>