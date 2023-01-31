<%-- 
    Document   : AddManu_Cate
    Created on : Jun 23, 2022, 12:00:55 AM
    Author     : ADMIN
--%>
<%@page import="Models.*"%>
<%@page import="DAL.DAO_LoadManu_Cate"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Manu Cate</title>
        <link rel="stylesheet" href="CSS/AddProductCSS.css"/>
        <link rel="stylesheet" href="CSS/bootstrap.min.css"/>
    </head>
    <body>
        <% DAO_LoadManu_Cate dao = (DAO_LoadManu_Cate)request.getAttribute("DAO_LoadManu_Cate");%>
        <jsp:include page="../Member/Menu.jsp"></jsp:include>
        <div class="table_content left">
            <form action="LoadManu_Cate" method="post">


                <h2>Maufacturer</h2>
                <table class="table table-striped">
                    <tr>
                        <th>Manufacture ID</th>
                        <th>Name</th>
                        <th>Address</th>                  
                    </tr>
                    <c:forEach items="${requestScope.DAO_LoadManu_Cate.manuList}" var="manu">
                        <tr>
                            <td>${manu.manufacturerId}</td>
                            <td>${manu.name}</td>
                            <td>${manu.address}</td>
                            <td><a href='Update_Delete_Manu_Cate?type=0&manufacturerId=${manu.manufacturerId}'>Delete</a></td>
                            <td><a href='Update_Delete_Manu_Cate?type=1&manufacturerId=${manu.manufacturerId}'>Update</a></td>        
                        </tr>
                    </c:forEach>
                </table>
                <h2>Category</h2>
                <table class="table table-striped">
                    <tr>
                        <th>Category ID</th>
                        <th>Name</th>
                        <th>Content</th>                  
                    </tr>
                    <c:forEach items="${requestScope.DAO_LoadManu_Cate.cateList}" var="cate">
                        <tr>
                            <td>${cate.categoryId}</td>
                            <td>${cate.name}</td>
                            <td>${cate.content}</td>
                            <td><a href='Update_Delete_Manu_Cate?type=2&categoryId=${cate.categoryId}'>Delete</a></td>
                            <td><a href='Update_Delete_Manu_Cate?type=3&categoryId=${cate.categoryId}'>Update</a></td> 
                        </tr>
                    </c:forEach>
                </table>
        </div>
        <%
           Object obj = request.getAttribute("UpdateManu");
           String manuId ="";
           String nameManu = "";
           String address ="";
           if(obj != null){
               manuId = (obj+"").trim();
               if(manuId.length()!=0){
                   for(Manufacturer mn : dao.getManuList()){
                       if(mn.getManufacturerId().equals(manuId)){
                           nameManu = mn.getName();
                           address = mn.getAddress();                           
                           break;
                       }
                   }
               }
           }
        
        %>

        <%
            Object obj2 = request.getAttribute("UpdateCate");
            String cateId ="";
            String nameCate = "";
            String content ="";
            if(obj2 != null){
                cateId = (obj2+"").trim();
                if(cateId.length()!=0){
                    for(Category ct : dao.getCateList()){
                        if(ct.getCategoryId().equals(cateId)){
                            nameCate = ct.getName();
                            content = ct.getContent();                           
                            break;
                        }
                    }
                }
            }
        
        %>
        <div class="insert right">
            <h2>Manufacturer</h2>
            <table>
                <tr>
                    <td>Manufacturer ID</td>
                    <td><input type="text" name="manufacturerId" value="<%=manuId%>"></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="manuName" value="<%=nameManu%>"></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><input type="text" name="address" value="<%=address%>"></td>
                </tr>
            </table>
            <h2>Category</h2>
            <table>
                <tr>
                    <td>Category ID</td>
                    <td><input type="text" name="categoryId" value="<%=cateId%>"></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="cateName" value="<%=nameCate%>"></td>
                </tr>
                <tr>
                    <td>Content</td>
                    <td><input type="text" name="content" value="<%=content%>"></td>
                </tr>
            </table>
            <input type="submit" value="Insert">
        </div>
    </form>
</body>
</html>
