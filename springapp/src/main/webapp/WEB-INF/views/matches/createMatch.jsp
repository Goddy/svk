<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<form:form action="createMatch.html" cssClass="form-horizontal" role="form" modelAttribute="form">
    <div class="form-group">
        <div class="col-sm-10">
            <label for="homeTeam" class="col-sm-2 control-label"><spring:message code="text.homeTeam"/></label>

            <div class="col-sm-10">
                <form:select id="homeTeam" path="homeTeam" cssClass="form-control">
                    <c:forEach items="${teams}" var="team">
                        <form:option value="${team.id}" label="${team.name}"/>
                    </c:forEach>
                </form:select>
                <td><form:errors path="homeTeam" cssClass="error"/></td>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <label for="awayTeam" class="col-sm-2 control-label"><spring:message code="text.awayTeam"/></label>

            <div class="col-sm-10">
                <form:select id="awayTeam" path="awayTeam" cssClass="form-control">
                    <c:forEach items="${teams}" var="team">
                        <form:option value="${team.id}" label="${team.name}"/>
                    </c:forEach>
                </form:select>
                <td><form:errors path="awayTeam" cssClass="error"/></td>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <label for="season" class="col-sm-2 control-label"><spring:message code="text.season"/></label>

            <div class="col-sm-10">
                <form:select id="season" path="season" cssClass="form-control">
                    <c:forEach items="${seasons}" var="season">
                        <form:option value="${season.id}" label="${season.description}"/>
                    </c:forEach>
                </form:select>
                <td><form:errors path="season" cssClass="error"/></td>
            </div>
        </div>
    </div>
    //Todo: implement calendar
    <div class="form-group">
        <div class="form-group">
            <div class="col-sm-10">
                <label class="col-sm-2 control-label" for="date">
                    <spring:message code="text.date"/>
                </label>

                <div class="col-sm-10">
                    <div id="trDate" class="input-group">
                        <span class="input-group-addon">
                        </span>
                        <input id="date" value="" name="date" data-date-format="DD/MM/YYYY" class="form-control date"
                               placeholder="DD/MM/JJJJ" type="text">
                    </div>
                    <td><form:errors path="date" cssClass="error"/></td>
                </div>
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
<%@ include file="../jspf/datePickerDependencies.jspf" %>