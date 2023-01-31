<%-- 
    Document   : Home
    Created on : Jun 25, 2022, 2:13:23 PM
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
        <link rel="stylesheet" href="CSS/Home.css">
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>


            <div class="row">

                <div class="banner">

                    <img  src="img/images.jpg" />

                </div>

            </div>
            <div class="container-fluid">
                <div class="row Trang-chu">
                    <div class="row Trang_chu-left col-md-3">
                        <div class="category">
                            <table>
                                <tr>
                                    <th>Danh mục</th>
                                </tr>
                            <c:forEach items="${requestScope.DAO_Home.cateList}" var="cate" >
                                <tr>
                                    <td><a href="LoadHome?categoryId=${cate.categoryId}">${cate.name}</a></td>         
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>

                <div class="row Trang_chu-right col-md-9">
                    <c:if test="${requestScope.productList.isEmpty()}">
                        <h3 class="col-md-12">Không tìm thấy sản phẩm</h3>
                    </c:if>
                    <c:forEach items="${requestScope.productList}" var="i" begin="${CP.begin}" end="${CP.end}">
                        <div class="list_sp col-md-4">
                            <div class="sp">
                                <div class="image_sp">
                                    <a href="GetProduct?productid=${i.productId}">
                                        <img src="${i.imgUrl}" alt="">
                                    </a>
                                </div>

                                <div class="text-box">
                                    <a href="GetProduct?productid=${i.productId}">
                                        <p>${i.name}</p>
                                    </a>
                                    <div class="price">
                                        <fmt:formatNumber type="Currency" currencySymbol="" value="${i.price - i.discount}"/>
                                        đ
                                    </div>
                                        
                                    <c:choose>
                                        <c:when test="${!(sessionScope.user.admin == 0) }">
                                            <c:choose>
                                                <c:when test="${i.amount >0}">
                                                    <form name="f" action="buy?id=${i.productId}" method="post">
                                                        <input type="submit" value="Buy Item">
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Đã hết hàng</p>
                                                </c:otherwise>

                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <button><a href='Update_Delete_Product?type=1&productid=${i.productId}'>Update</a></button>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div>
                        <form action="LoadHome" method="post">
                            <c:if test="${CP.cp!=0}">
                                <input type="submit" name="home" value="Home">
                                <input type="submit" name="pre" value="Pre">
                            </c:if>

                            <c:forEach begin="${CP.pageStart}" end="${CP.pageEnd}" var="i">
                                <span><input type="submit" name="btn${i}" value="${i+1}"></span>
                                </c:forEach>
                                <c:if test="${CP.cp!=CP.np-1}">
                                <input type="submit" name="next" value="Next">
                                <input type="submit" name="end" value="End">
                            </c:if>
                            <input type="text" hidden name="cp" value="${CP.cp}">
                            <input type="text" hidden name="np" value="${CP.np}">
                            <select name="nrpp">
                                <c:forEach items="${CP.arrNrpp}" var="i" varStatus="loop">
                                    <option value="${loop.index}"
                                            <c:if test="${loop.index==sessionScope.nrpp}">
                                                selected
                                            </c:if>
                                            >${i}
                                    </option>
                                </c:forEach>
                            </select>
                        </form>
                    </div>
                </div>
            </div>
            <jsp:include page="Footer.jsp"></jsp:include>

    </body>

</html>
<script type="text/javascript">
    function buy(id) {

        document.f.action = "buy?id=CSHS35&num=1";
        document.f.submit();
    }

</script>
