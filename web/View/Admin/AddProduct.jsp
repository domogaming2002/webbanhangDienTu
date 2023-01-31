<%-- 
    Document   : AddProduct
    Created on : Jun 19, 2022, 10:52:42 PM
    Author     : ADMIN
--%>

<%@page import="Models.*"%>
<%@page import="DAL.DAO_Product"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>
        <link rel="stylesheet" href="CSS/AddProductCSS.css"/>
        <link rel="stylesheet" href="CSS/bootstrap.min.css"/>
        <script src='tinymce/tinymce.min.js'></script>
        <script>
            tinymce.init({
                selector: '#myTextarea',
                height: 300,

            });
        </script>
    </script>
</head>
<body>
    <jsp:include page="../Member/Menu.jsp"></jsp:include>
    <% DAO_Product dao = (DAO_Product)request.getAttribute("DAO_Product");%>
    <p>
    <div class="table_content ">
        <p class="text-danger">${mess}</p>
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Content</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>Discount</th>
                    <th>Imgage</th>
                    <th>Imgage Url</th>
                    <th>ManufacturerID</th>
                    <th>CategoryID</th>
                </tr>    
            </thead>


            <c:forEach items="${requestScope.DAO_Product.prdList}" var="prd">
                <tr>
                    <td>${prd.productId}</td>
                    <td>${prd.name}</td>
                    <td class="content">${prd.content}</td>
                    <td><fmt:formatNumber type="currency" value=" ${prd.price}" currencySymbol=""/>đ</td> 
                    <td>${prd.amount}</td>
                    <td><fmt:formatNumber type="currency" value=" ${prd.discount}" currencySymbol=""/>đ</td> 
                    <td><img src="${prd.imgUrl}" alt="image"></td>
                    <td>${prd.imgUrl}</td>

                    <c:forEach items="${requestScope.DAO_Product.getManuHm()}" var="manu">
                        <c:if test="${prd.manufacturerId == manu.key}">
                            <td>${manu.value.getName()}</td>
                        </c:if>
                    </c:forEach>
                    <c:forEach items="${requestScope.DAO_Product.getCateHm()}" var="cate">
                        <c:if test="${prd.categoryId == cate.key}">
                            <td>${cate.value.getName()}</td>
                        </c:if>
                    </c:forEach>
                    <td><a href='Update_Delete_Product?type=0&productid=${prd.productId}'>Delete</a></td>
                    <td><a href='Update_Delete_Product?type=1&productid=${prd.productId}'>Update</a></td>        

                </tr>
            </c:forEach>
        </table>
    </div>        

    <%
        Object obj = request.getAttribute("UpdateProduct");
        String productid ="";
        String name = "";
        String content ="";
        String price = "";
        String amount = "";
        String discount = "";
        String imgUrl ="";
        String manufacturerId = "";
        String categoryId ="";
        if(obj != null){
            productid = (obj+"").trim();
            if(productid.length()!=0){
                for(Product prd: dao.getPrdList()){
                    if(prd.getProductId().equals(productid)){
                        name = prd.getName();
                        content = prd.getContent();
                        price = prd.getPrice() + "";
                        amount = prd.getAmount() + "";
                        discount = prd.getDiscount()+ "";
                        imgUrl = prd.getImgUrl();
                        manufacturerId = prd.getManufacturerId();
                        categoryId = prd.getCategoryId();
                        break;
                    }
                }
            }
        }
        
    %>
    <div class="insert">
        <form action="LoadProduct_Manu_Cate" method = 'post'>
            <table>
                <tr>
                    <td>Product ID </td>
                    <td><input type="text" name="productid" required value="<%=productid%>" maxlength="50"></td>
                </tr>
                <tr>
                    <td>Name Product</td>
                    <td><input type="text" name="name" required value="<%=name%>" maxlength="50" ></td>
                </tr>
                <tr>
                    <td>Content</td>
                    <td> 
                        <textarea name="content" id="myTextarea" class="form-control" maxlength="500">
                            <%=content%>
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="number" name="price" required value="<%=price%>"></td>
                </tr>
                <tr>
                    <td>Amount</td>
                    <td><input type="number" name="amount" required value="<%=amount%>"></td>
                </tr>
                <tr>
                    <td>Discount</td>
                    <td><input type="number" name="discount" value="<%=discount%>"></td>
                </tr>
                <tr>
                    <td>Image URL</td>
                    <td><input class="form-control" type="file" name="imgUrl" required value="<%=imgUrl%>"></td>
                </tr>
                <tr>
                    <td>Manufacturer ID</td>
                    <td><select name='manufacturerId'>
                            <%for (Map.Entry<String, Manufacturer> en: dao.getManuHm().entrySet()){          
                                String key = en.getKey();
                                Manufacturer val = en.getValue();%>
                            <option value ='<%=key%>'
                                    <%=(key.equals(manufacturerId)?"selected":"")%> >
                                <%=val.getName()%>
                            </option>
                            <%}%>  
                    </td>
                </tr>
                <tr>
                    <td>Category ID</td>
                    <td><select name='categoryId'>
                            <%for (Map.Entry<String, Category> en: dao.getCateHm().entrySet()){          
                                String key = en.getKey();
                                Category val = en.getValue();%>
                            <option value ='<%=key%>'
                                    <%=(key.equals(categoryId)?"selected":"")%>>
                                <%=val.getName()%>
                            </option>
                            <%}%>  
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input class="btn btn-primary" type="submit" value="Insert"></td>
                </tr>

            </table>
        </form>
    </div>

</body>
</html>
