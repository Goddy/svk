<%@ include file="jspf/header.jspf" %>
<h2>
    <spring:message code="title.welcome"/>
</h2>

<img class="img-responsive img-thumbnail" src="<c:url value="/resources/images/team.jpg"/>"/>

<div class="panel panel-default">
    <div class="panel-heading"><spring:message code="title.welcome.about"/></div>
    <div class="panel-body">
        <spring:message code="text.welcome.about"/>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading"><spring:message code="title.welcome.website"/></div>
        <div class="panel-body">
            <spring:message code="text.welcome.website"/>
        </div>
    </div>

<%@ include file="jspf/footer.jspf" %>

