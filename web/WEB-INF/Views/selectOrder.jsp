<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="Elige tu Pedido" />
<%@ include file="includes/header.jspf" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow border-primary">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">¿Dónde quieres añadir este producto?</h4>
                </div>
                <div class="card-body">
                    <div class="d-flex align-items-center mb-4">
                        <img src="${pageContext.request.contextPath}/${requestScope.productTemp.pathImage}" 
                             style="width: 60px; height: 60px; object-fit: cover;" class="rounded me-3">
                        <div>
                            <h5 class="mb-0">${requestScope.productTemp.name}</h5>
                            <span class="badge bg-secondary">Talla: ${requestScope.sizeTemp}</span>
                        </div>
                    </div>

                    <p>Tienes varios pedidos abiertos. Selecciona uno:</p>
                    
                    <div class="list-group">
                        <c:forEach items="${sessionScope.user.cart.activeOrders}" var="order" varStatus="status">
                            <form action="${pageContext.request.contextPath}/cart" method="POST" class="list-group-item list-group-item-action p-0">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="id" value="${requestScope.productTemp.id}">
                                <input type="hidden" name="size" value="${requestScope.sizeTemp}">
                                <input type="hidden" name="orderIdx" value="${status.index}">
                                
                                <button type="submit" class="btn btn-link text-decoration-none text-dark w-100 text-start p-3 d-flex justify-content-between align-items-center">
                                    <span><strong>Pedido #${status.count}</strong> <small class="text-muted">(${order.orderDate})</small></span>
                                    <span class="badge bg-primary rounded-pill">${order.totalAmount}€</span>
                                </button>
                            </form>
                        </c:forEach>
                    </div>
                    
                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-link text-secondary">Cancelar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>