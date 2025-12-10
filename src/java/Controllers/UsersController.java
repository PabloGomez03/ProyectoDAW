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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author apolo
 */
@MultipartConfig
@WebServlet(name = "UsersController", urlPatterns = {"/profile/*", "/modify", "/admin", "/remove/*"})
public class UsersController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String view = "";
        String action = "/login";

        if (request.getServletPath().equals("/profile")) {

            if (request.getPathInfo() != null) {
                action = request.getPathInfo();
            }

        } else if (request.getServletPath().equals("/admin")) {

            action = "/admin";

        } else if (request.getServletPath().contains("/remove")) {

            action = "/remove";

        } else {

            action = "/error";

        }

        switch (action) {

            case "/admin" -> {

                User s = (User) session.getAttribute("user");
                if (s.getRole().equals("admin-user")) {

                    loadData(request);
                    view = "administration";

                } else {

                    view = "error";

                }

            }

            case "error" -> {

                view = "error";

            }
            case "/remove" -> {

                Long id = Long.valueOf(request.getPathInfo().substring(1));
                deleteUser(id);
                loadData(request);
                view = "administration";

            }

            default -> {

                view = "profile";

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

        if (request.getServletPath().equals("/modify")) {

            action = "/modify";

        } else {

            action = "/error";

        }

        switch (action) {

            case "/error" -> {

                view = "error";

            }

            case "/modify" -> {

                modifyUser(request, session);
                view = "modifyOK";

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void modifyUser(HttpServletRequest request, HttpSession session) throws IOException, ServletException {

        try {
            utx.begin();
            User u = (User) session.getAttribute("user");

            u.setName(request.getParameter("name"));
            u.setEmail(request.getParameter("email"));
            u.setAddress(request.getParameter("address"));
            String hashedPassword = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt(12));
            u.setPassword(hashedPassword);

            Part img = request.getPart("image");

            if (img != null && img.getSize() > 0) {

                String nombre = img.getSubmittedFileName();
                String extension = "";
                int i = nombre.lastIndexOf('.');
                if (i > 0) {
                    extension = nombre.substring(i);
                }

                String filename = "user_" + u.getId() + extension;

                String upload = request.getServletContext().getRealPath("")+ File.separator + "/img/users";

                File uploadDir = new File(upload);
                
                if(!uploadDir.exists()){
                    
                    uploadDir.mkdir();
                    
                }

                File file = new File(uploadDir, filename);
                try (InputStream input = img.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                u.setProfileImage(filename);
            }

            em.merge(u);

            utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadData(HttpServletRequest resquest) {

        try {
            utx.begin();

            List<Product> ProductList = null;
            List<User> UserList = null;

            TypedQuery<Product> pquery = em.createQuery("SELECT p FROM Product p", Product.class);
            ProductList = pquery.getResultList();

            TypedQuery<User> uquery = em.createQuery("SELECT u FROM User u", User.class);
            UserList = uquery.getResultList();

            resquest.setAttribute("plist", ProductList);
            resquest.setAttribute("ulist", UserList);

            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteUser(Long id) {

        try {
            utx.begin();

            em.remove(em.find(User.class, id));

            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
