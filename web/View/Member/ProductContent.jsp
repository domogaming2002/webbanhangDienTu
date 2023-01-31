<%-- 
    Document   : ProductContent
    Created on : Jun 27, 2022, 1:55:07 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <link rel='stylesheet' href='CSS/san_pham.css'>
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
        <c:set value="${requestScope.product}" var="i"/>
        <div class="container">
            <div class="row San_pham">
                <div class="Hinh_anh col-md-3">
                    <img src="${i.imgUrl} " alt="">
                </div>

                <div class="Information col-md-6">
                    <div class="Information MaSP Rate">
                        <span>Mã sp: ${i.productId} </span>
                        <span>| Đánh giá: ${requestScope.countStar}<i class="fa-solid fa-star" style="color: yellow"></i></span>
                        <span>| Số lượt bình luận:${requestScope.countComment} </span>
                    </div>

                    <div class="Information Thong_tin">
                        <h2>${i.name}</h2>
                        <h5>Thông tin về sản phẩm</h5>
                        <p>${requestScope.product.content}</p>

                    </div>

                    <div class="Information Gia">
                        <span>Giá gốc:<fmt:formatNumber type="Currency" currencySymbol="" value=" ${i.price}"/>đ</span>
                        <c:if test="${i.discount !=0}">
                            <span>  được giảm lên đến <fmt:formatNumber type="Currency" currencySymbol="" value="${i.discount}"/>đ</span><br/>
                            <span>Giá sau khi giảm:<fmt:formatNumber type="Currency" currencySymbol="" value="${i.price - i.discount}"/>đ</span>
                        </c:if>

                        <p>Giá đã bao gồm VAT</p>
                        <p>Bảo hành 12 Tháng</p>
                    </div>

                    <c:choose>
                        <c:when test="${i.amount > 0}">
                            <div class="Gio_Hang">
                                <form name="f" action="buy?id=${i.productId}" method="post">
                                    <input type="submit" value="Buy Item">
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p>Đã hết hàng</p>
                        </c:otherwise>
                    </c:choose>  


                </div>

                <div class="col-md-3">
                    <div>
                        <p>Chính sách mua hàng</p>
                        <p>Uy tín trong giới shopgame</p>
                        <p>sản phẩm chính hãng 100%</p>
                        <p>Trả góp 0%</p>
                        <p>Vệ sinh PC, Laptop trọn đời</p>
                    </div>

                    <div>
                        <p>Chính sách giao hàng</p>
                        <p>Giao hàng siêu tốc trong 2h</p>
                        <p>Giao hàng miễn phí toàn quốc</p>
                        <p>Nhận hàng và thanh toán tại nhà (ship COD)</p>
                    </div>
                </div>
            </div>
            <h3 style="text-align: center">Bình luận</h3>
            <div style="border: 1px solid black">
                <c:choose>
                    <c:when test="${requestScope.allreview.isEmpty()}">
                        <div>
                            <h3 style="text-align: center">Chưa có bình luận nào</h3>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach items="${requestScope.allreview}" var="r">
                                <div style="font-size: 20px" class="col-md-2">
                                    <i class="fa-solid fa-user-large"></i>
                                    <c:forEach items="${requestScope.userInfor}" var="us">
                                        <c:if test="${us.userId eq r.userId }">
                                            ${us.firstName} ${us.lastName}
                                        </c:if>
                                    </c:forEach>

                                </div>
                                <div class="col-md-10" style="font-size: 30px">
                                    <fmt:formatNumber pattern="###.#" value="${r.star}"/>
                                    <i class="fa-solid fa-star" style="color: yellow"></i>
                                    <p readonly="">
                                        ${r.comment}
                                    </p>
                                </div>

                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

                <c:if test="${requestScope.checkComment == true}">
                    <div>
                        <button class="btn btn-secondary" onclick="comment()">Nhấn để bình luận</button>
                    </div>
                </c:if>            
                <div id="comment" style="display: none">
                    <form action="" method="post">
                        <input type="radio" name="star" value="1">1<i class="fa-solid fa-star" style="color: yellow"></i>
                        <input type="radio" name="star" value="2">2<i class="fa-solid fa-star" style="color: yellow"></i>
                        <input type="radio" name="star" value="3">3<i class="fa-solid fa-star" style="color: yellow"></i>
                        <input type="radio" name="star" value="4">4<i class="fa-solid fa-star" style="color: yellow"></i>
                        <input type="radio" name="star" value="5" checked="">5<i class="fa-solid fa-star" style="color: yellow"></i>
                        <textarea class="form-control"   name="comment" placeholder="Hãy nhập bình luận của bạn">
                        
                        </textarea>
                        <input class="btn btn-primary" type="submit" value="Bình luận">
                        <input type="hidden" name="productId" value="${i.productId}">
                    </form>

                </div>
            </div>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
<script>
    function comment() {
        var x = document.getElementById('comment');
        if (x.style.display == "none") {
            x.style.display = "inline";
        } else {
            x.style.display = "none";
        }

    }

</script>
