<%-- 
    Document   : updateRecord2
    Created on : Nov 3, 2015, 8:54:49 PM
    Author     : John Phillips
--%>

<%@page import="model.Face" %>
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
        <h2>Update The Face</h2>
        <form action="update" method="get">
            <% Face face = (Face) request.getAttribute("face");%>
            Patient Id: <input type="text" name="faceId" value="<%= face.getFaceId() %>" readonly>
            <br><br>
            Email: <input type="text" name="name" size="20" value="<%= face.getName() %>" required>
            <br><br>
            Blood Sugar: <input type="number" name="rating" value="<%= face.getRating() %>" required>
            <br><br>
            Date: <input type="text" name="eyebrowRow" size="5" value="<%= face.getEyebrowRow() %>" required>
            <br><br>            
            Event: <input type="text" name="eyeRow" size="5" value="<%= face.getEyeRow()%>" required>
            <br><br>
            Health: <input type="text" name="mouthRow" size="5" value="<%= face.getMouthRow()%>" required>
            <br><br>
            Notes: <input type="text" name="quote" size="60" value="<%= face.getQuote()%>" required>
            <br><br>
            

            <input type="hidden" name="action" value="updateRecord2">

            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>
