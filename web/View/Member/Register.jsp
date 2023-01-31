<%-- 
    Document   : Register
    Created on : Jun 28, 2022, 8:09:51 PM
    Author     : ADMIN
--%>
<%@page import="Models.*"%>
<%@page import="DAL.DAO_Login"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
        <link rel='stylesheet' type='text/css' href='CSS/dangky.css'>
    </head>
    <body>
        
        <div id="Form_DangKy">
            <div id="logo">
                <a href="Home"><img src="img/Screenshot 2022-06-14 170037.jpg.png" alt=""></a>
            </div>
            <form action="Register" method="post">
                <p class="text-danger">${mess}</p>
                <div class="input username">
                    <input type="text" name="userId" placeholder="Username" required="" maxlength="20">
                </div>

                <div class="input password">
                    <input type="password" name="pass" placeholder="Enter password" required="" maxlength="20"><br>
                </div>

                <div class="input repassword">
                    <input type="password" name="repass" placeholder="Enter repassword"required="" maxlength="20">
                </div>

                <div class="input name">       
                    <input type="text" name="firstname" placeholder="Enter FirstName" required="" maxlength="50">
                    <input type="text" name="lastname" placeholder="Enter LastName" required="" maxlength="50">
                </div>

                <div class="input date">
                    <input type="date" name="dateofbirth" placeholder="Date of birth" required="">Date of Birth
                </div>


                <div class="input email">
                    <input type="email" name="gmail" placeholder="Enter Email" required="" maxlength="50">
                </div>

                <div class="input question">
                    <select name="qid" >
                        <c:forEach items="${requestScope.DAO_Login.questHm}" var="m">
                            <option value ='${m.key}' >
                            ${m.value.getQname()}
                        </option>
                        </c:forEach>
                       
                    </select>
                    <input type="text" name="answer" placeholder="Enter your answer" required="" maxlength="100">
                </div>

                <div class="input Phone">
                    <input type="number" name="phone" placeholder="Enter your phone" required="" maxlength="15">
                </div>

                <div class="input Address">
                    <input type="text" name="address" placeholder="Enter your address" required="" maxlength="100">
                </div>

                <div class="Register_Under">
                    <div class="Login">
                        <a href="Login" style="color: ">Đã có tài khoản</a>
                    </div>
                    <input type="submit" name="Register" value="Register" class="Register">
                    <div>Or register with</div><br>
                    <div class="Register Email">
                        <i class="fa-brands fa-google"></i>
                        <a href="#">Register with Google</a>
                    </div>
                </div>
            </form>
        </div>
        
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
