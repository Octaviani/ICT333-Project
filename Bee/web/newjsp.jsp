<%-- 
    Document   : newjsp
    Created on : 25/04/2015, 11:21:55 PM
    Author     : swoorup
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <jsp:useBean id="mybean" scope="session" class="mypackage.User" />
        
        <jsp:setProperty name="mybean" property="name" value="" />
        <h1>Hello World! <jsp:getProperty name="mybean" property="name" /></h1>
    </body>
</html>
