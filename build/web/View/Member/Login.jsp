<%-- 
    Document   : Login
    Created on : Jun 27, 2022, 9:04:46 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel='stylesheet' type='text/css' href='CSS/dangnhap.css'>
        <script src='JS/dangnhap.js'></script>
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <div id="Form_DangNhap">
            <div id="logo">
                <a href="Home"><img src="img/Screenshot 2022-06-14 170037.jpg.png" alt=""></a>
            </div>

            <form action="Login" method="post">
                <p class="text-danger">${mess}</p>
                <div class="input Username">
                    <i class="fa-solid fa-circle-user"></i>
                    <input type="text" name="userId" placeholder="Type your UserId"><br>
                </div>
                <div class="input Password">
                    <i class="fa-solid fa-unlock"></i>
                    <input type="password" name="pass" placeholder="Type your Password" id="pass">
                    <i class="fa-solid fa-eye icon" onclick="showPass()"></i>
                </div>
                <div class="DangNhap_Under">
                    <div class="Forget_Password">
                        <span><a href="Register">Đăng ký tài khoản</a></span>
                        <a href="#">Forget Password?</a>

                    </div>


                    <input type="submit" value="Login" class="Login"><br>
                    <span>Or sign with</span><br>
                    <div class="Login Email">
                        <i class="fa-brands fa-google"></i>
                        <a href="#">Login with Google</a>
                    </div>
                </div>
            </form>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
