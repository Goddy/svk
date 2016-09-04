<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<script type="text/javascript">
    $(document).ready(function () {
        setTimeout('redirect()', '8000');
    });
    function redirect() {
        window.location = "/account/edit.html";
    }

</script>
<div class="col-md-12">
    <div class="box">
        <c:choose>
            <c:when test="${sessionScope.lastSessionException ne null}">
                <div class="alert alert-danger" role="alert"><spring:message code="text.error.social.duplicate"/></div>
            </c:when>
            <c:otherwise>

                <h1><spring:message code="title.account.connected"/></h1>

                <p><spring:message code="text.social.connect.success"/></p>

            </c:otherwise>
        </c:choose>
        <a class="btn btn-info" href="<c:url value="/account/edit.html"/>"><spring:message code="button.back"/> </a>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>


