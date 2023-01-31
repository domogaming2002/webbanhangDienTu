<%-- 
    Document   : Menu
    Created on : Jun 26, 2022, 9:18:27 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="Header_Nav">
    <nav class="navbar navbar-inverse dieu_huong">
        <a href="Home"><img src="img/Screenshot 2022-06-14 170037.jpg.png" alt="logoshop" class="navbar-left"></a>
        <div class="navbar_text">
            <ul class="nav navbar-nav ">


                <li> <a href="Home">Trang chủ</a> </li>
                <li><a href="#">About</a></li>


            </ul>			
            <form class="navbar-right">
                <ul class="nav navbar-nav">
                    <c:if test="${sessionScope.user == null}">

                        <li><a href="Login">Đăng Nhập</a></li>
                        <li><a href="Register">Đăng ký</a></li>

                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <li><a href="UpdateUserDetail">Hello ${sessionScope.user.userId}</a></li>
                        <li><a href="Logout">Đăng xuất</a></li>

                        
                    </c:if>
                    <c:if test="${sessionScope.user != null && sessionScope.user.admin == 0 }">
                        <li><a href="LoadProduct_Manu_Cate">Product</a></li>
                        <li><a href="LoadManu_Cate">Manu-Cate</a></li>
                        <li><a href="LoadOrder">Đơn hàng</a></li>
                        <li><a href="ManageUser">Người dùng</a></li>
                        </c:if>

                    <c:if test="${sessionScope.user != null && sessionScope.user.admin == 1 }">
                        <li><a href="LoadOrder">Lịch sử mua hàng</a></li>
                        <li><a href="chat">Chat với Admin</a></li>
                        <li><a href="show"><i class="fa-solid fa-cart-shopping"></i>(${requestScope.size})item</a></li>
                        </c:if>
                </ul>
            </form>
        </div>


        <form action="LoadHome" method="get" >
            <div class="form-group">
                <input type="text" name="search" class="form-control" placeholder="Search">
            </div>
            <button type="submit" class="btn btn-default">Search</button>    
        </form>



    </nav>

</div>