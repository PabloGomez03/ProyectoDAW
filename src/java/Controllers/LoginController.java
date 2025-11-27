/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Product;
import Models.ShoppingCart;
import Models.User;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
@WebServlet(name = "LoginController", urlPatterns = {"/login", "/signup","/signup/*", "/logout", ""})
public class LoginController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private static final Logger userLog = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "";
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

        } else if (request.getServletPath().isEmpty()) {

            action = "";

        } else if (request.getServletPath().equals("/logout")) {

            action = "/logout";
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

            case "/logout" -> {

                session.removeAttribute("user");
                session.removeAttribute("login");
                session.removeAttribute("sign");
                view = "index";
                loadIndex(request);

            }

            case "/error" -> {

                view = "error";

            }
            case "" -> {
                
                view = "index";
                loadIndex(request);

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "";
        String action = "/login";

        String name, email, password, address;

        if (request.getServletPath().equals("/login")) {

            if (request.getPathInfo() != null) {

                action = request.getPathInfo();
            }

        } else if (request.getServletPath().equals("/signup")) {

            action = request.getContextPath();

        } else {

            action = "error";

        }

        switch (action) {

            case "/login" -> {

                email = request.getParameter("email");
                password = request.getParameter("password");
                        

                User u = logUser(email,password);

                if (u != null) {
                    
                    session.setAttribute("user", u);
                    view = "loginOK";
                    
                } else {

                    request.setAttribute("login", "Los datos introducidos no son correctos!");
                    view = "login";
                }

            }
            case "/save" -> {

                name = request.getParameter("name");
                email = request.getParameter("email");
                password = request.getParameter("password");
                address = request.getParameter("address");

                ShoppingCart cart = new ShoppingCart();

                User user = new User(cart, name, email, password, address, "user");

                if(saveUser(user)){
                    
                    view = "signupOK";
                    
                    
                }
                else{
                    
                    request.setAttribute("sign", "Ya existe un usuario con ese email registrado!!");
                    view = "signup";
                    
                }

                

            }
            case "/error" ->{
                
                view = "error";
                
            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }


    public boolean saveUser(User u) {

        boolean cond = false;
        
        try {
            utx.begin();
            
            User user = em.find(User.class, u.getId());
            
            if(user == null){
                
                em.persist(u);
                userLog.log(Level.INFO, "New User saved");
                cond = true;
                
            }
            
            utx.commit();
        } catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            userLog.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
        finally{
            
            return cond;
            
        }
    }

    public User logUser(String email,String password) {

        User u = null;

        try {
            utx.begin();

            Query q = em.createNamedQuery("User.findByEmail");
            q.setParameter("qemail", email);

            u = (User) q.getSingleResult();
            
            if(!u.getPassword().equals(password)){
                
                u = null;
                
            }

            utx.commit();

        } catch (HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException | IllegalStateException | SecurityException e) {
            userLog.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        } finally {

            return u;

        }

    }
    public void loadIndex(HttpServletRequest request){
        
        try {
                    utx.begin();

                    List<Product> list = null;

                    TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
                    query.setMaxResults(4);
                    list = query.getResultList();
                    

                    request.setAttribute("list", list);

                    utx.commit();
                } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
    }

}
