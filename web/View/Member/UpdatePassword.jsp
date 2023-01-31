<%-- 
    Document   : UpdatePassword
    Created on : Jul 1, 2022, 1:48:07 PM
    Author     : ADMIN
--%>
<%@page import="Models.*"%>
<%@page import="DAL.DAO_Login"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel='stylesheet' type='text/css' href='CSS/UserDetail.css'>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <script>
            function check() {
                var result = confirm("Bạn chắc chắn muốn thay đổi thông tin không?");
                document.getElementById('update').value = result;
            }

        </script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="changePass">

                <form  action="UpdateUserPassword" method="post">
                    <p class="text-danger">${msg}</p>
                <div class="input Passs">
                    <c:forEach items="${requestScope.DAO_Login.questHm}" var="m">
                        <c:if test="${m.key eq sessionScope.user.qId}">
                            <div class="input username">
                                <p>${m.value.getQname()}</p>
                            </div>
                        </c:if>
                    </c:forEach>
                    <input type="text" name="answer" placeholder="Enter your anwer">
                </div>
                <div class="input Pass">
                    <input type="password" name="oldpassword" placeholder="Enter old Password" maxlength="50">
                </div>
                <div class="input Pass">
                    <input type="password" name="newpassword" placeholder="Enter new Password" maxlength="50">
                </div>
                <div class="input Pass">
                    <input type="password" name="renewpassword" placeholder="Re-enter new Password" maxlength="50">
                </div>
                <div class="input submit">
                    <input type="submit" value="Update Password" onclick="check()">
                    <input type="hidden" name="update" id="update" value="">
                </div>  
            </form> 

        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
