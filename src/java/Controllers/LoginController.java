/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.ShoppingCart;
import Models.User;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author apolo
 */

@WebServlet(name = "LoginController", urlPatterns = {"/login", "/login/*", "/signup", "/signup/*"})
public class LoginController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private static final Logger userLog = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session =request.getSession();

        String view = "error";
        String action = "/login";

        if (request.getServletPath().equals("/login")) {

            if (request.getPathInfo() != null) {
                action = request.getPathInfo();
            }

        } else if (request.getServletPath().equals("/signup")) {
            
             action = "/signup";
             
            if (request.getPathInfo() != null) {
                action = request.getPathInfo();
            }
            
           
        } else {

            action = "error";

        }

        switch (action) {

            case "/login" -> {

                view = "login";

            }
   
            case "/signup" -> {

                view = "signUser";

            }
            case "/error" -> {

                view = "error";

            }
            
            

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session =request.getSession();

        String view = "";
        String action = "/login";

        String name, email, password;

        if (request.getServletPath().equals("/login")) {

            if(request.getPathInfo() != null){
                
                action = request.getPathInfo();
            }
            

        } else if (request.getServletPath().equals("/signup")) {

            action = "/signup";
            
            
        } else {

            action = "error";

        }

        switch (action) {

            case "/login" -> {

                email = request.getParameter("email");
                
                User u = logUser(email);
                
                if(u != null){
                    
                    if(u.getName().equals("admin")){
                        
                    session.setAttribute("id", 1);
                    session.setAttribute("role","admin");
                        
                    }
                    else{
                        
                    session.setAttribute("id", 1);
                    session.setAttribute("role","user");
                        
                    }
                    
                    view = "loginOK";
                }
                else{
                    
                    view = "error";
                }

            }
            case "/signup" -> {

                name = request.getParameter("name");
                email = request.getParameter("email");
                password = request.getParameter("password");

                ShoppingCart cart = new ShoppingCart();

                User user = new User(cart, name, email, password, "", "");

                saveUser(user);

                view = "signupOK";

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void saveUser(User u) {

        Long id = u.getId();
        try {
            utx.begin();
            if (id == null) {
                em.persist(u);
                userLog.log(Level.INFO, "New User saved");
            } else {
                userLog.log(Level.INFO, "User {0} updated", id);
                em.merge(u);
            }
            utx.commit();
        } catch (Exception e) {
            userLog.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public User logUser(String email) {

        User u = null;

        try {
            utx.begin();

            Query q = em.createNamedQuery("User.findByEmail");
            q.setParameter("qemail", email);

            u = (User) q.getSingleResult();

            utx.commit();

        } catch (Exception e) {
            userLog.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        } finally {

            return u;

        }

    }

}
