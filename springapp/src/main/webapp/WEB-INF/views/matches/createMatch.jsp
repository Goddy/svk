<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<h2><spring:message code="title.createMatch" arguments="${match.description}"/> </h2>
<div class="panel panel-default">
    <div class="panel-body">
<form:form action="createMatch.html" cssClass="form-horizontal" role="form" modelAttribute="form">
    <tag:formField path="homeTeam" label="text.homeTeam" title="text.homeTeam" type="empty" optional="false">
        <form:select id="homeTeam" path="homeTeam" cssClass="form-control">
            <c:forEach items="${teams}" var="team">
                <form:option value="${team.id}" label="${team.name}"/>
            </c:forEach>
        </form:select>
    </tag:formField>
    <tag:formField path="awayTeam" label="text.awayTeam" title="text.awayTeam" type="empty" optional="false">
        <form:select id="awayTeam" path="awayTeam" cssClass="form-control">
            <c:forEach items="${teams}" var="team">
                <form:option value="${team.id}" label="${team.name}"/>
            </c:forEach>
        </form:select>
    </tag:formField>
    <tag:formField path="season" label="text.season" title="text.season" type="empty" optional="false">
        <form:select id="season" path="season" cssClass="form-control">
            <c:forEach items="${seasons}" var="season">
                <form:option value="${season.id}" label="${season.description}"/>
            </c:forEach>
        </form:select>
    </tag:formField>

    <tag:formField path="date" label="text.date" title="text.date" type="empty" optional="false">
        <input id="date" value="" name="date" data-date-format="DD/MM/YYYY" class="form-control date" placeholder="DD/MM/JJJJ" type="text">
    </tag:formField>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default"><spring:message code="button.add"/></button>
        </div>
    </div>
</form:form>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
<%@ include file="../jspf/datePickerDependencies.jspf" %>