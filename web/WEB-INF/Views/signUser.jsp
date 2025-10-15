<%-- 
    Document   : formUser
    Created on : 8 oct 2025, 13:13:16
    Author     : apolo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario</title>
    </head>
    <body>
        <h1>DresStyle</h1>
        <h3>Añadir Usuario</h3>

        <form action="/daw/signup" method="POST">
        <label for="name">Nombre:</label>
        <input id="name" type="text" name="name"><br />
        <label for="email">Correo:</label>
        <input id="email" type="text" name="email"><br />
        <label for="phone">Teléfono: </label>
        <input id="phone" type="text" name="phone"><br />
        <label for="password">Contraseña </label>
        <input id="password" type="text" name="password"><br />
        <input type="submit" value="Guardar" href ="/signup/OK"/>
    </body>
</html>
