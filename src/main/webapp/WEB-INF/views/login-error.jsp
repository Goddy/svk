<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='login_error'%>
<%@ include file="header.jsp"%>

<h1>Login failed</h1>
        <login_error:if test="${'fail' eq param.auth}">
                <div style="color:red">
                Login Failed!!!<br />
                Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
         		</div>
        </login_error:if>
<%@ include file="footer.jsp"%>