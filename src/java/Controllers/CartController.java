/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Order;
import Models.Product;
import Models.ProductItem;
import Models.ShoppingCart;
import Models.User;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.logging.Logger;

/**
 *
 * @author apolo
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart", "/cart/*"})
public class CartController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private static final Logger userLog = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "/error";
        String action = "";

        User role = (User) session.getAttribute("user");

        if (role == null) {

            action = "/error";

        } else {

            if (request.getServletPath().equals("/cart")) {

                if (request.getPathInfo() == null) {

                    action = request.getServletPath();

                } else {

                    action = request.getPathInfo();

                }

            } else {

                action = "/error";
            }

        }

        switch (action) {

            case "/cart" -> {

                view = "cart";

            }

            case "/error" -> {

                view = "error";

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "error"; // Por defecto
        String action = "";

        User role = (User) session.getAttribute("user");

        // ... (Tu bloque de validación de usuario y path sigue igual) ...
        if (role == null || !role.getRole().equals("user")) {
            action = "/error";
        } else {
            if (request.getServletPath().equals("/cart")) {
                if (request.getPathInfo() == null) {
                    action = request.getServletPath();
                } else {
                    action = request.getPathInfo();
                }
            } else {
                action = "/error";
            }
        }

        switch (action) {

            case "/newcart" -> {
                ShoppingCart c = role.getCart();
                c.addOrder(new Order());
                view = "cart";
            }

            case "/add" -> {
                
                addProduct(request, role);

                
                if (request.getAttribute("selectOrderMode") != null) {
                    view = "selectOrder"; 
                } else {
                    view = "cart"; 
                }
            }

            case "/error" -> {
                view = "error";
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);
    }

    public void addProduct(HttpServletRequest request, User u){
        try {
            Long prodId = Long.parseLong(request.getParameter("id"));
            String size = request.getParameter("size");
            String orderIdxStr = request.getParameter("orderIdx");
            
            // 1. Recuperamos el producto para tener sus datos
            Product p = em.find(Product.class, prodId);
            
            // 2. LÓGICA DE DECISIÓN: ¿Tiene el usuario varios pedidos y no ha elegido?
            // Si hay > 1 pedido Y no nos viene el índice -> Hay que preguntar
            if (u.getCart().getActiveOrders().size() > 1 && (orderIdxStr == null || orderIdxStr.isEmpty())) {
                
                // Preparamos los datos para la vista 'selectOrder.jsp'
                request.setAttribute("productTemp", p);
                request.setAttribute("sizeTemp", size);
                request.setAttribute("selectOrderMode", true); // "Bandera" para el doPost
                return; // Cortamos aquí, no añadimos nada todavía
            }

            // 3. Si llegamos aquí, es porque añadimos directo (0, 1 pedido o ya eligió)
            ProductItem item = new ProductItem(size, p.getName(), p.getDescription(), p.getPrice(), 1, p.getCategory(), p.getPathImage());
            item.setId(p.getId());

            Order targetOrder = null;
            
            // Intentar coger el pedido por índice si viene
            if (orderIdxStr != null && !orderIdxStr.isEmpty()) {
                int index = Integer.parseInt(orderIdxStr);
                if (index >= 0 && index < u.getCart().getActiveOrders().size()) {
                    targetOrder = u.getCart().getActiveOrders().get(index);
                }
            }

            // Si no hay target (porque es automático o índice inválido)
            if (targetOrder == null) {
                if (u.getCart().getActiveOrders().isEmpty()) {
                    // Caso 0 pedidos -> Crear nuevo
                    targetOrder = new Order(new java.util.Date(), 0, "Pendiente");
                    u.getCart().addOrder(targetOrder);
                } else {
                    // Caso 1 pedido (o fallback) -> Usar el último
                    targetOrder = u.getCart().getLastOrder();
                }
            }
            
            // Añadir y recalcular
            targetOrder.addItem(item);
            targetOrder.setTotalAmount(targetOrder.calculateTotal()); 

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

}
