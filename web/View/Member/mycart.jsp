<%-- 
    Document   : mycart
    Created on : Jul 6, 2022, 5:00:57 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="CSS/cart.css">
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
        <script>
            function check() {
                var result = confirm("Bạn chắc chắn muốn mua hàng không?");
                document.getElementById('buycart').value = result;
            }

        </script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="cart">
                <h1>Shopping cart online </h1>
                <table border="1px" width="40%">
                    <tr>
                        <th>No</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total money</th>
                        <th>Action</th>
                    </tr>

                <c:set var="o" value="${requestScope.cart}"/>
                <c:set var="tt" value="0"/>
                <c:forEach items="${o.items}" var="i">
                    <c:set var="tt" value="${tt+1}"/>
                    <tr>
                        <td>${tt}</td>
                        <td>${i.product.name}</td>
                        <td>
                            <button><a href="process?num=-1&id=${i.product.productId}">-</a></button>
                            ${i.quantity}
                            <button><a href="process?num=1&id=${i.product.productId}">+ </a></button>
                        </td>
                        <td class="tr">
                            <fmt:formatNumber type="currency" value=" ${i.price}" currencySymbol=""/>
                            đ
                        </td>
                        <td class="tr">
                            <fmt:formatNumber type="currency"  currencySymbol="" value=" ${i.price * i.quantity}"/>đ
                        </td>
                        <td style="text-align: center">
                            <form action="process" method="post">
                                <input type="hidden" name="id" value="${i.product.productId}">

                                <input type="submit" value="return item"/>
                            </form>
                        </td>

                    </tr>
                </c:forEach>
            </table>
            <h3>
                Total money:<fmt:formatNumber type="Currency" currencySymbol="" value=" ${o.totalMoney}"/>đ
            </h3><hr/>
            <c:choose>
                <c:when test="${requestScope.cart.getItems().size() == 0}">
                    <p>Chưa có sản phẩm nào</p>
                </c:when>
                <c:otherwise>
                    <form action="checkout" method="post">
                        <input type="submit" value="Check out" onclick="check()">
                        <input type="hidden" name="buycart" id="buycart" value="">
                    </form>
                </c:otherwise>
            </c:choose>

            <hr/>
            <a href="Home">Tiếp tục mua sắm</a>
        </div>

        <jsp:include page="Footer.jsp"></jsp:include>
    </body>

</html>
