<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${empty list}">
    <div class="col-12 text-center">
        <p class="text-muted">No se encontraron productos.</p>
    </div>
</c:if>

<c:forEach var="prod" items="${requestScope.list}">
    <div class="col-lg-3 col-md-4 col-sm-6 mb-4 fade-in"> 
        <div class="card h-100">
            <img src="${pageContext.request.contextPath}/${prod.pathImage}" 
                 class="card-img-top" 
                 alt="${prod.name}"
                 style="height: 250px; object-fit: cover;"> 
            
            <div class="card-body d-flex flex-column">
                <h5 class="card-title">${prod.name}</h5>
                <p class="card-text fw-bold text-primary">${prod.price}€</p>
                
                <c:choose>
                    <c:when test="${prod.category != 'Bolsos'}">
                        <select class="form-select mb-2" id="talla-${prod.id}">
                            <option value="S">S</option>
                            <option value="M" selected>M</option>
                            <option value="L">L</option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="talla-${prod.id}" value="Única">
                        <p class="text-muted small mb-2">Talla Única</p>
                    </c:otherwise>
                </c:choose>

                <div class="mt-auto">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <button type="button" class="btn btn-primary w-100" 
                                    onclick="gestionarCompra(${prod.id}, 'talla-${prod.id}')">
                                <i class="bi bi-cart-plus"></i> Añadir al Carrito
                            </button>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary w-100">
                                Inicia sesión
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                </div>
        </div>
    </div>
</c:forEach>