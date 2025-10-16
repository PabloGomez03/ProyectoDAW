<%-- 
    Document   : login
    Created on : 15 oct 2025, 11:26:55
    Author     : apolo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        
        <h1>DresStyle</h1>
        <h3>Iniciar Sesion</h3>

        <form action="/daw/login" method="POST">
        <label for="email">Correo:</label>
        <input id="email" type="text" name="email"><br />
        <label for="password">Contraseña </label>
        <input id="password" type="text" name="password"><br />
        <input type="submit" value="Guardar" href ="/login/OK"/>
        
    </body>
</html>
