<%-- 
    Document   : Order
    Created on : Jul 12, 2022, 9:43:05 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <table class="table table-striped">
                <tr>
                    <th>OrderId</th>
                    <th>Total Money</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            <c:forEach items="${requestScope.Order}" var="ord">
                <tr>
                    <td>${ord.orderId}</td>
                    <td><fmt:formatNumber type="currency" value="${ord.total}" currencySymbol=""/>đ</td>
                    <td>${ord.phone}</td>
                    <td>${ord.address}</td>
                    <td>${ord.date}</td>

                    <td>
                        <c:forEach items="${requestScope.DAO_Order.stsHm}" var="s">
                            <c:if test="${s.key eq ord.statusId}">
                                ${s.value.getStatusDetail()}
                            </c:if>                              
                        </c:forEach>
                    </td>
                    <td>
                        <form action="LoadOrder" method="post">
                            <input type="hidden" name="orderId" value="${ord.orderId}">
                            <input type="submit" value="Xem thông tin đơn hàng">
                            <input type="hidden" name="statusid" value="${ord.statusId}">
                        </form>
                    </td>
                    <td>
                        <c:if test="${ord.statusId == 1 && sessionScope.user.admin == 1}">
                            <form action="UpdateOrderStatus" method="post">
                                <input type="hidden" name="orderId" value="${ord.orderId}">
                                <input type="hidden" name="statusid" value="5">
                                <input type="submit" value="Hủy đơn hàng">
                            </form>
                        </c:if>
                    </td>

                </tr>
            </c:forEach>
        </table>
        <jsp:include page="Footer.jsp"></jsp:include>

    </body>
</html>
