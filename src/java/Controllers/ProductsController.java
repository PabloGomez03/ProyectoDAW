/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Product;
import Models.User;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author apolo
 */
@WebServlet(name = "ProductsController", urlPatterns = {"/products/*", "/products/delete/*"})
public class ProductsController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger userLog = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "", action = "/error";

        if (request.getServletPath().equals("/products")) {

            action = request.getPathInfo();

        } else if (request.getServletPath().equals("/products/delete")) {

            action = request.getPathInfo();

        } else if (request.getServletPath().equals("/products/search")) {

            action = "/search";

        } else if (request.getServletPath().equals("/products/searchAjax")) {

            action = "/searchAjax";

        }

        switch (action) {

            case "/new" -> {

                view = "newproduct";

            }
            case "/searchAjax" -> {

                
                loadQuery(request);
                view = "productFrag";

            }
            case "/search" -> {

                
                loadQuery(request);
                view = "searchres"; 

            }

            default -> {

                Long id = Long.valueOf(request.getPathInfo().substring(1));
                deleteProduct(id);
                loadData(request);
                view = "administration";

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "", action = "/error";

        if (request.getServletPath().equals("/products")) {

            action = request.getPathInfo();

        }

        switch (action) {

            case "/create" -> {

                String name, price, stock, pathImage, description, category;

                name = request.getParameter("name");
                price = request.getParameter("price");
                stock = request.getParameter("stock");
                pathImage = request.getParameter("pathImage");
                description = request.getParameter("description");
                category = request.getParameter("category");

                Product p = new Product(name, description, Float.valueOf(price), Integer.valueOf(stock), category, "img/" + pathImage);
                saveProduct(p);
                loadData(request);
                view = "administration";

            }

        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    public void deleteProduct(Long id) {

        try {
            utx.begin();

            em.remove(em.find(Product.class, id));
            userLog.log(Level.INFO, "Product " + id + " deleted!");

            utx.commit();
        } catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {

        }

    }

    public void loadData(HttpServletRequest resquest) {

        try {

            List<Product> ProductList = null;
            List<User> UserList = null;

            TypedQuery<Product> pquery = em.createQuery("SELECT p FROM Product p", Product.class);
            ProductList = pquery.getResultList();

            TypedQuery<User> uquery = em.createQuery("SELECT u FROM User u", User.class);
            UserList = uquery.getResultList();

            resquest.setAttribute("plist", ProductList);
            resquest.setAttribute("ulist", UserList);

        } catch (SecurityException | IllegalStateException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadQuery(HttpServletRequest request) {
    String queryText = request.getParameter("q"); 
    List<Product> results = null;

    try {
        if (queryText != null && !queryText.trim().isEmpty()) {
            TypedQuery<Product> q = em.createQuery(
                    "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:search)",
                    Product.class
            );
            q.setParameter("search", "%" + queryText + "%");
            results = q.getResultList();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

   
    request.setAttribute("list", results); 
    request.setAttribute("searchQuery", queryText);
}

    public boolean saveProduct(Product p) {

        boolean cond = false;

        try {
            utx.begin();

            if (p.getId() == null) {

                em.persist(p);
                userLog.log(Level.INFO, "New Product saved");
                cond = true;

            }

            utx.commit();
        } catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            userLog.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        } finally {

            return cond;

        }

    }

}
