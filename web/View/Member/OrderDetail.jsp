<%-- 
    Document   : OrderDetail
    Created on : Jul 12, 2022, 7:07:11 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='CSS/Order.css'>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
        <h2 style="text-align: center">Chi tiết đơn hàng</h2>
        <c:if test="${sessionScope.user.admin == 0 && !(requestScope.statusid eq '5')}">
            <form action="UpdateOrderStatus" method="post">
                <select name="statusid">
                    <c:forEach items="${requestScope.DAO_Order.stsHm}" var="s">
                        <c:choose>
                            <c:when test="${s.key eq requestScope.statusid}">
                                <option value="${s.key}" selected="">${s.value.getStatusDetail()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${s.key}">${s.value.getStatusDetail()}</option>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </select>
                <input hidden="" name="orderId" value="${requestScope.orderId}">
                <input type="submit" value="Cập nhật trạng thái">
            </form>

        </c:if >
        <c:if test="${sessionScope.user.admin == 0 && requestScope.statusid eq '5'}">
            <h3 style="text-align: center">Đơn hàng đã bị hủy</h3>
        </c:if>
        <table  class="table table-bordered" >
            <tr>
                <th>OrderId</th>
                <th>ProductId</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>    

            <c:forEach items="${requestScope.OrderDetail}" var="ord" >
                <tr>
                    <td>${ord.orderId}</td>
                    <td>
                        <c:forEach items="${requestScope.DAO_Order.prdHM}" var="m">
                            <c:if test="${m.key eq ord.productId}">
                                ${m.value.getName()}
                            </c:if>                              
                        </c:forEach>
                    </td>
                    <td>${ord.quantity}</td>
                    <td><fmt:formatNumber type="currency" value="${ord.price}" currencySymbol=""/>đ</td>
                </tr>
            </c:forEach>
        </table>
        <button ><a href="LoadOrder">Quay lại</a></button>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
