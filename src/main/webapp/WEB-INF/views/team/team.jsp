<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<div class="alert alert-info"><spring:message code="info.teams"/></div>

<security:authorize access="isAuthenticated()" var="loggedIn"/>


<div class="team-row">
    <c:forEach items="${players['GOALKEEPER']}" var="p">
        <div class="col-sm-4 col-lg-4 col-md-4">
            <div class="thumbnail">
                <c:choose>
                    <c:when test="${not empty p.accountProfile.avatar.imageUrl}">
                        <img src="${p.accountProfile.avatar.imageUrl}">
                    </c:when>
                    <c:otherwise>
                        <img src="https://placeholdit.imgix.net/~text?txtsize=24&txt=<spring:message code="text.no.image"/>&w=200&h=200"
                             alt="">
                    </c:otherwise>
                </c:choose>

                <div class="caption">
                    <c:if test="${!loggedIn}">
                        <tag:static-formField label="label.firstName" title="label.firstName" value="${p.firstName}"
                                              type="h4"/>
                    </c:if>
                    <c:if test="${loggedIn}">
                        <tag:static-formField label="label.name" title="label.name" value="${p}" type="h4"/>
                        <tag:static-formField label="label.email" title="label.email" value="${p.username}"
                                              type="email"/>
                        <tag:static-formField label="label.phone" title="label.phone" value="${p.accountProfile.phone}"
                                              type="phone"/>
                        <tag:static-formField label="label.mobile" title="label.mobile"
                                              value="${p.accountProfile.mobilePhone}" type="mobile"/>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<script src="<c:url value='/resources/js/svk-ui-1.3.js'/>"></script>

<script type="text/javascript">
    (function ($, dd, md) {

    })(jQuery, svk.deleteDialogs, svk.mapDialog);
</script>
<%@ include file="../jspf/footer.jspf" %>