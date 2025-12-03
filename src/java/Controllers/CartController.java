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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (user.getCart() == null) {
            user.setCart(new ShoppingCart());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/cart.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (user.getCart() == null) {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user); 
            user.setCart(newCart);

            
            em.merge(user);
        }

        String pathInfo = request.getPathInfo();
        String action = "";

        if (pathInfo != null && !pathInfo.equals("/")) {
            action = pathInfo;
        } else {

            String paramAction = request.getParameter("action");
            if (paramAction != null) {
                action = "/" + paramAction;
            }
        }

        switch (action) {
            case "/newcart" -> {
                // Crear nuevo pedido
                ShoppingCart c = user.getCart();
                Order o = new Order(new Date(), 0, "Pendiente");
                c.addOrder(o);
                saveOrder(o);
            }

            case "/add" -> {
                //Añadir producto al carrito
                addProduct(request, user);
            }
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    public Order addProduct(HttpServletRequest request, User u) {

        Order targetOrder = null;

        try {
            Long prodId = Long.parseLong(request.getParameter("id"));
            String size = request.getParameter("size");
            String orderIdxStr = request.getParameter("orderIdx");

            Product p = em.find(Product.class, prodId);

            ProductItem item = new ProductItem(size, p.getName(), p.getDescription(), p.getPrice(), 1, p.getCategory(), p.getPathImage());
            item.setId(p.getId());

            //Si viene un índice de la ventana Modal, intentamos usar ese pedido
            if (orderIdxStr != null && !orderIdxStr.isEmpty()) {
                try {
                    int index = Integer.parseInt(orderIdxStr);
                    if (index >= 0 && index < u.getCart().getActiveOrders().size()) {
                        targetOrder = u.getCart().getActiveOrders().get(index);
                    }
                } catch (NumberFormatException e) {
                }
            }

            //Si no hay target (automático), usamos lógica por defecto
            if (targetOrder == null) {
                if (u.getCart().getActiveOrders().isEmpty()) {
                    targetOrder = new Order(new Date(), 0, "Pendiente");

                    u.getCart().addOrder(targetOrder);
                } else {
                    targetOrder = u.getCart().getLastOrder();
                }
            }

            targetOrder.addItem(item);
            targetOrder.setTotalAmount(targetOrder.calculateTotal());
           
                utx.begin();
                User managedUser = em.merge(u); 
                request.getSession().setAttribute("user", managedUser);
                utx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        
        return targetOrder;

    }

    public void saveOrder(Order o) {
        try {
            utx.begin();

            if (o.getId() == null) {

                em.persist(o);
                
            } else {

                em.merge(o);
            }

            utx.commit();
        } catch (Exception e) {
            userLog.log(Level.SEVERE, "Error guardando el pedido", e);
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
        }
    }
}
