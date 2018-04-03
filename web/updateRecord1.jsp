<%-- 
    Document   : updateRecord1
    Created on : Nov 3, 2015, 8:37:40 PM
    Author     : John Phillips
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>faceBase</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="mystyle.css">
    </head>
    <body>
        <h1><a href="home.html">FaceBase</a></h1>
        <h2>Update A Face</h2>
        <form action="update" method="get">
            Record number: <input type="number" name="faceId" placeholder="Record id to update" required>
            <br><br>

            <input type="hidden" name="action" value="updateRecord1">
            
            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>
