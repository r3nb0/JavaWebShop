<%-- 
    Document   : error
    Created on : Jan 23, 2022, 9:42:35 PM
    Author     : r3nb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error!</title>
    </head>
    <body>
        <h1>${errorMessage}</h1>
        <% session.removeAttribute("errorMessage"); %>
    </body>
</html>
