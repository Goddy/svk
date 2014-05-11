<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<form:form action="create_match" cssClass="form-horizontal" role="form" modelAttribute="form">
    <div class="form-group">
        <label for="homeTeam" class="col-sm-2 control-label"><spring:message code="text.homeTeam"/></label>

        <div class="col-sm-10">
            <select class="form-control" id="homeTeam">
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <label for="awayTeam" class="col-sm-2 control-label"><spring:message code="text.awayTeam"/></label>

            <div class="col-sm-10">
                <form:select id="awayTeam" path="awayTeam">
                    <c:forEach items="${teams}" var="team">
                        <form:option value="${team.id}" label="${team.name}"/>
                    </c:forEach>
                </form:select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <label for="homeTeam" class="col-sm-2 control-label"><spring:message code="text.date"/></label>

            <div class="col-sm-10">
                <form:select id="homeTeam" path="homeTeam">
                    <c:forEach items="${teams}" var="team">
                        <form:option value="${team.id}" label="${team.name}"/>
                    </c:forEach>
                </form:select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <label for="season" class="col-sm-2 control-label"><spring:message code="text.date"/></label>

            <div class="col-sm-10">
                <form:select id="season" path="season">
                    <c:forEach items="${seasons}" var="season">
                        <form:option value="${season.id}" label="${season.description}"/>
                    </c:forEach>
                </form:select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default"><spring:message code="button.add"/></button>
        </div>
    </div>
</form:form>

<%@ include file="../jspf/footer.jspf" %>
