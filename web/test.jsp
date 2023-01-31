<%-- 
    Document   : test
    Created on : Jun 27, 2022, 5:17:50 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src='tinymce/tinymce.min.js'></script>
    <script>
        tinymce.init({
            selector: '#myTextarea',
            height: 300,
           
        });
    </script>
    </head>
    <body>
            <textarea name="description" id="myTextarea" class="form-control">
    </textarea>
    </body>
</html>
