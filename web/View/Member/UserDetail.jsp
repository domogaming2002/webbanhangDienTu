<%-- 
    Document   : UserDetail
    Created on : Jun 30, 2022, 8:22:10 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
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
            <div id="Form_DangKy">
                <form action="UpdateUserDetail" method="post">
                    <div class="input username" style="text-align: center">
                        <h3>Hello ${sessionScope.user.userId}</h3>
                         <p class="text-danger">${mess}</p>
                </div>

                <div class="input">
                    <p>Tên</p>
                    <input type="text" name="firstname" value="${sessionScope.user.firstName}" required="" maxlength="50">                
                    
                </div>
                <div class="input">
                       <p>Họ</p>     
                    <input type="text" name="lastname" value="${sessionScope.user.lastName}" required="" maxlength="50">
                </div>

                <div class="input">
                    <input type="date" name="dateofbirth" value="${sessionScope.user.dob}">Date of Birth
                </div>

                <div class="input Phone">
                    <p>Số điện thoại</p>
                    <input type="number" name="phone" value="${sessionScope.user.phone}" maxlength="15" required="">
                </div>

                <div class="input Address">
                    <p>Địa chỉ</p>
                    <input type="text" name="address" value="${sessionScope.user.address}" maxlength="100" required="">
                </div>
                
                <div class="input ">
                    <p>Email</p>
                    <input type="email" name="email" value="${sessionScope.user.email}" maxlength="100" required="">
                </div>

                <div class="input submit">
                    <input type="submit" value="Udate Information" onclick="check()">
                </div>

                <input type="hidden" name="update" id="update" value="" />
                <input type="hidden" name="userid"  value="${sessionScope.user.userId}"/>
            </form>
            <form action="UpdateUserPassword" method="post">
                <input type="submit" value="Update Password"/>
            </form>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
