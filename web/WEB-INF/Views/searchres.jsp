<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Resultados: ${requestScope.searchQuery}" />
<c:set var="activePage" value="search" />

<%@ include file="includes/header.jspf" %>

<div class="container mt-4">
    
    <h2 class="mb-4">
        Resultados para: <span class="text-primary">"${requestScope.searchQuery}"</span>
        <small class="text-muted fs-6">(${searchResults != null ? searchResults.size() : 0} productos encontrados)</small>
    </h2>

    <div class="row">
        
        <c:if test="${empty searchResults}">
            <div class="col-12 text-center py-5">
                <i class="bi bi-search" style="font-size: 3rem; color: #ccc;"></i>
                <p class="mt-3 lead">No encontramos productos con ese nombre.</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-dark">Ver todo el catálogo</a>
            </div>
        </c:if>

        <c:forEach var="p" items="${requestScope.searchResults}">
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card h-100 shadow-sm">
                    
                    <img src="${pageContext.request.contextPath}/${p.pathImage}" class="card-img-top" alt="${p.name}" 
                         style="height: 200px; object-fit: cover;">
                    
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${p.name}</h5>
                        <p class="card-text text-muted small">${p.category}</p>
                        
                        <h4 class="mb-3 text-primary">${p.price}?</h4>
                        
                        <div class="mt-auto d-grid gap-2">
                            
                            <form action="${pageContext.request.contextPath}/" method="POST">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="id" value="${p.id}">
                                
                                <select class="form-select mb-2" name="size">
                                    <option value="S">Talla S</option>
                                    <option value="M" selected>Talla M</option>
                                    <option value="L">Talla L</option>
                                </select>

                                <button type="submit" class="btn btn-success w-100">
                                    <i class="bi bi-cart-plus"></i> Añadir
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
