<%-- 
    Document   : Chat
    Created on : Jul 17, 2022, 3:10:35 PM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='CSS/Order.css'>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/cc5cf43e7a.js" crossorigin="anonymous"></script>
        <title>JSP Page</title>
        <style>
            p{
                padding: 5px; 
                border-radius: 20px;
                font-size: 20px;
            }

            .id{
                font-size: 30px;
                font-weight: 600;
            }
            .R{
                background-color: #E8f1f3;
                margin-left: 300px;
            }
            .L{

                background-color: #Efefef;
                margin-right: 300px;
            }

        </style>
    </head>
    <body style="background-color: rgb(188, 231, 253)">
        <jsp:include page="Menu.jsp"></jsp:include>
            <h2 style="text-align: center">Chat</h2>
            <div class="container" style="border: 2px solid gray; margin-top: 20px; background-color: white;width: 800px">
            <c:forEach items="${requestScope.chatList}" var="c"> 
                <c:if test="${c.userId_Send eq sessionScope.user.userId}">
                    <p style="text-align: right" class="id">${c.userId_Send}</p>
                    <p style="text-align: right"  class="R">${c.chat}</p>
                </c:if>
                <c:if test="${!(c.userId_Send eq sessionScope.user.userId)}">
                    <p style="text-align: left" class="id">${c.userId_Send}</p>
                    <p style="text-align: left" class="L">${c.chat}</p>
                </c:if>
            </c:forEach>
            <c:if test="${sessionScope.user.admin ==1}">
                <form action="chat" method="post" id="chatinput">
                    <input type="text" class="form-control" name="chatMessage" style="display: inline; width: 700px;height: 50px;font-size: 20px;margin: 15px 0px" >
                    <input type="submit" class="btn btn-primary" style="font-size: 15px" value="Gửi">
                    <input type="hidden" name="threadId" value="${requestScope.threadId}" form="chatinput">
                </form>
            </c:if>
            <c:if test="${sessionScope.user.admin == 0}">
                <form action="ManageChatAdmin" method="post" id="chatinput">
                    <input class="form-control" name="chatMessage" style="display: inline; width: 700px;height: 50px;font-size: 20px" >
                    <input type="hidden" name="userID_Member" value="${requestScope.userID_Member}">
                    <input type="hidden" name="threadId" value="${requestScope.threadId}" form="chatinput">
                    <input type="submit" class="btn btn-primary" style="font-size: 15px" value="Gửi">
                </form>
            </c:if>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
