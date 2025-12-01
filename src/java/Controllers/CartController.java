/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Order;
import Models.ShoppingCart;
import Models.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author apolo
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart", "/cart/*"})
public class CartController extends HttpServlet {

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
            
            case "/add" -> {
                
                

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

        String view = "/error";
        String action = "";

        User role = (User) session.getAttribute("user");

        if (!role.getRole().equals("user")) {

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

            case "/error" -> {

                view = "error";

            }

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/" + view + ".jsp");
        rd.forward(request, response);

    }

}
