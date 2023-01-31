<%-- 
    Document   : Verify
    Created on : Jul 7, 2022, 3:08:30 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel='stylesheet' type='text/css' href='CSS/UserDetail.css'>
        <link href="CSS/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
        <div style="text-align: center">
            <p>Chúng tôi đã gửi mail đến email của bạn. vui lòng kiểm tra</p>
            <form id="vrf" action="VerifyServlet" method="post">
                <input type="text" name="tokenverify" placeholder="Enter your code" required="" maxlength="6">
                <input type="submit" value="Verify">
                <input type="hidden" name="resend" id="resend" value="false">
                <p onclick="res()">Click để gửi lại mã</p>

            </form>    
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
<script>
    function res() {
        document.getElementById("resend").value = "true";
        document.querySelector("#vrf").submit();
    }
</script>
