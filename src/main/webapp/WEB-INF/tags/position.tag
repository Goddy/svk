<%@tag description="position component" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="players" required="true" type="java.util.List" %>

<c:if test="${not empty players}">
    <security:authorize access="isAuthenticated()" var="loggedIn"/>

    <div class="team-row row well">
        <h3 class="text-center"><spring:message code="${title}"/></h3>
        <c:forEach items="${players}" var="p">
            <div class="col-sm-4 col-lg-4 col-md-4">
                <div class="thumbnail avatar">
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
                            <tag:static-formField label="label.mobilePhone" title="label.mobilePhone"
                                                  value="${p.accountProfile.mobilePhone}" type="mobile"/>
                            <c:set value="${p.accountProfile.address.address}<br/>${p.accountProfile.address.postalCode} ${p.accountProfile.address.city}" var="address"/>
                            <tag:static-formField label="label.address" title="label.address" type="address"
                                                  value="${not empty p.accountProfile.address ? address : null }"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>