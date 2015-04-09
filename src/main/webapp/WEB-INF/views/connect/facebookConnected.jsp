<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<script type="text/javascript">
    $(document).ready(function(){
        setTimeout('redirect()','5000');
    });
    function redirect(){
        window.location = "/account/edit.html";
    }

</script>
<c:choose>
    <c:when test="${requestScope['social.addConnection.duplicate'] ne null}">
        <div class="alert alert-danger" role="alert"><spring:message code="text.error.social.duplicate"/></div>
    </c:when>
    <c:otherwise>
        <h1><spring:message code="title.account.connected"/></h1>
        <form:form action="${pageContext.request.contextPath}/connect/facebook" method="DELETE">
            <div class="formInfo">
                <spring:message code="text.social.connect.success"/>
            </div>
            <button type="submit">Disconnect</button>
        </form:form>
    </c:otherwise>
</c:choose>
<a class="btn btn-info" href="<c:url value="/account/edit.html"/>"><spring:message code="button.back"/> </a>

<%@ include file="../jspf/footer.jspf" %>


