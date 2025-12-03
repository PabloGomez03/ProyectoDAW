<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Mi Carrito" />
<%@ include file="includes/header.jspf" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Mis Pedidos en Curso</h2>
        <form action="${pageContext.request.contextPath}/cart/newcart" method="POST">
            <input type="hidden" name="action" value="newOrder">
            <button class="btn btn-outline-primary">
                <i class="bi bi-plus-lg"></i> Nueva Cesta
            </button>
        </form>
    </div>

    <c:if test="${empty sessionScope.user.cart.activeOrders}">
        <div class="alert alert-warning">No tienes pedidos activos.</div>
    </c:if>

    <c:forEach var="order" items="${sessionScope.user.cart.activeOrders}" varStatus="status">
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-light d-flex justify-content-between">
                <h5 class="mb-0">Pedido #${status.count} (${order.state})</h5>
                <small>${order.orderDate}</small>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead><tr><th>Producto</th><th>Talla</th><th>Precio</th></tr></thead>
                    <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>${item.name}</td>
                                <td><span class="badge bg-secondary">${item.size}</span></td>
                                <td>${item.price}€</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <h4>Total: ${order.totalAmount}€</h4>
                    <button class="btn btn-success" onclick="alert('Simulando pago de la Orden #${status.count}...')">
                        Pagar y Finalizar
                    </button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="includes/footer.jspf" %>
