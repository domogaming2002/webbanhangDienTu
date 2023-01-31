<%-- 
    Document   : ManageMember
    Created on : Jul 13, 2022, 9:54:36 AM
    Author     : ADMIN
--%>
<%@page import="Models.*"%>
<%@page import="DAL.DAO_Login"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/AddProductCSS.css"/>
        <link rel="stylesheet" href="CSS/bootstrap.min.css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <% DAO_Login dao = (DAO_Login)request.getAttribute("DAO_Login");%>
        <jsp:include page="../Member/Menu.jsp"></jsp:include>
            <table class="table table-bordered table-striped">
                <tr>
                    <th>UserId</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>email</th>
                    <th>Question</th>
                    <th>Answer</th>
                    <th>Date of Birth</th>
                    <th>Address</th>
                    <th>Action</th>
                    <th>Chat</th>


                </tr>
            <c:forEach items="${requestScope.member}" var="m">
                <tr>
                    <td>${m.userId}</td>
                    <td>${m.firstName}</td>
                    <td>${m.lastName}</td>
                    <td>${m.phone}</td>
                    <td>${m.email}</td>

                    <c:forEach items="${requestScope.questHm}" var="q">
                        <c:if test="${q.key eq m.qId}">
                        <div class="input username">
                            <td>${q.value.getQname()}</td>
                        </div>
                    </c:if>
                </c:forEach>

                <td>${m.answer}</td>
                <td>${m.dob}</td>
                <td>${m.address}</td>
                <td><a href='UpdateUserAdmin?userid=${m.userId}'>Update</a></td>         
                <td><a href="ManageChatAdmin?userId=${m.userId}" style="<c:forEach items="${requestScope.thread}" var="t">
                           <c:if test="${m.userId eq t.userId}">
                                ${t.isReply==false?'color:red':''}
                           </c:if>

                       </c:forEach>">Chat</a></td>
            </tr>
        </c:forEach>
    </table>

    <%
    Object obj = request.getAttribute("userupdate");
    String userid ="";
    String firstname = "";
    String lastname ="";
    String phone = "";
    String email = "";
    String qid = "";
    String answer ="";
    String dob = "";
    String address ="";
    if(obj != null){
        userid = (obj+"").trim();
        if(userid.length()!=0){
            for(User us: dao.getUsList()){
                if(us.getUserId().equals(userid)){
                    firstname = us.getFirstName();
                    lastname = us.getLastName();
                    phone = us.getPhone();
                    email = us.getEmail();
                    qid = us.getqId();
                    answer = us.getAnswer();
                    dob = us.getDob();
                    address = us.getAddress();
                    break;
                }
            }
        }
    }
        
    %>

    <div class="insert">
        <p class="text-danger">${requestScope.mess}</p>
        <form action="ManageUser" method = 'post'>
            <table>
                <tr>
                    <td>UserId</td>
                    <td><input type="text" name="userid" readonly="" value="<%=userid%>" maxlength="50"></td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstname" required value="<%=firstname%>" maxlength="50" ></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastname" required value="<%=lastname%>" maxlength="50" ></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input type="number" name="phone" required value="<%=phone%>"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" required value="<%=email%>"></td>
                </tr>
                <tr>
                    <td>Question</td>
                    <td><select name="qid">
                            <%for (Map.Entry<String, Question> en: dao. getQuestHm().entrySet()){          
                                        String key = en.getKey();
                                        Question val = en.getValue();%>
                            <option value ='<%=key%>'
                                    <%=(key.equals(userid)?"selected":"")%> >
                                <%=val.getQname()%>
                            </option>
                            <%}%>  
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Answer</td>
                    <td><input class="form-control" type="text" name="answer" required value="<%=answer%>"></td>
                </tr>
                <tr>
                    <td>DOB</td>
                    <td><input class="form-control" type="date" name="dob" required value="<%=dob%>"></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><input class="form-control" type="text" name="address" required value="<%=address%>"></td>
                </tr>

                <tr>
                    <td></td>
                    <td><input class="btn btn-primary" type="submit" value="Update"></td>
                </tr>

            </table>
        </form>
    </div>

    <jsp:include page="../Member/Footer.jsp"></jsp:include>
</body>
</html>
