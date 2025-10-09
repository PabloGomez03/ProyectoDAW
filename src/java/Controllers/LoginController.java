/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

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
import jakarta.transaction.UserTransaction;

/**
 *
 * @author apolo
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login", "/login/*","/signup"})
public class LoginController extends HttpServlet {

    @PersistenceContext(unitName = "DAWFinalPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String view="";
        String action ="/login";
        
        if(request.getServletPath().equals("/login")){
            
            if(request.getPathInfo() != null)
            action = request.getPathInfo();
            
        }
        else if(request.getServletPath().equals("/signup")){
            
            action = "/signup";
        }
        else{
            
            action = "error";
            
        }
        
        
        switch(action){
            
            case "/login" ->{
                
                
            }
            case "/signup" ->{
                
                view = "signUser";
                
            }
            case "/error" ->{
                
                view = "error";
                
            }
            
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/"+view+".jsp");
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
        
        
        String view;
        String action ="/login";
        
        if(request.getServletPath().equals("/login") && request.getPathInfo() != null){
            
            action = request.getPathInfo();
            
        }
       
        switch(action){
            
            case "/login" ->{
                
                
            }
            case "/sign" ->{
                
                
                
            }
            
        }
        
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

}
