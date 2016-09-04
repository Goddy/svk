<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ include file="../jspf/header.jspf" %>

<h2><spring:message code="title.goals" arguments="${match.description}"/></h2>

<div class="panel panel-default">
    <div class="panel-body">
        <form:form id="form" modelAttribute="form" action="changeMatch.html" cssClass="form-horizontal">
            <form:hidden path="matchId"/>
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
                <joda:format var="parsed" pattern="dd/MM/yyyy HH:mm" value="${match.date}"/>
                <input id="date" value="${parsed}" name="date" class="form-control date" placeholder="DD/MM/YYYY 00:00"
                       type="text">
            </tag:formField>
            <tag:formField path="status" label="label.status" title="label.status" type="empty"
                           optional="false">
                <select class="form-control" name="status" id="status">
                    <c:forEach items="${matchStatus}" var="s">
                        <option value="${s}" ${form.status == s ?'selected':''}><spring:message
                                code="label.match.status.${s}"/></option>
                    </c:forEach>
                </select>
            </tag:formField>
            <div id="statusText">
                <tag:formField path="statusText" label="label.match.status.cancelled"
                               title="label.match.status.cancelled" type="textarea"
                               value="${form.statusText}" optional="true" rows="5"/>
            </div>
            <div id="matchResult">
                <tag:formField path="htGoals" label="text.htGoals" title="text.htGoals" type="number"
                               value="${form.htGoals}" optional="false"/>
                <tag:formField path="atGoals" label="text.atGoals" title="text.atGoals" type="number"
                               value="${form.atGoals}" optional="false"/>
            <div id="goalsDiv">
                <tag:formField path="goals" label="text.goals" title="text.goals.description" type="empty"
                               optional="false">
                    <div class="form-inline" id="goalsForm">
                        <c:choose>
                            <c:when test="${not empty match.goals && fn:length(match.goals) gt 0}">
                                <c:forEach items="${match.goals}" var="goal">
                                <span class="space-bottom">
                                <input name="goals[${goal.order}].order" style="display: none" value="${goal.order}"/>
                                <select class="form-control" name="goals[${goal.order}].scorer">
                                    <option value=""><spring:message code="text.scorer"/></option>
                                    </option>
                                    <c:forEach items="${players}" var="player">
                                        <option value="${player.id}" ${goal.scorer.id==player.id?'selected':''}>${player}</option>
                                    </c:forEach>
                                </select>
                                <select class="form-control" name="goals[${goal.order}].assist">
                                    <option value=""><spring:message code="text.assist"/></option>
                                    </option>
                                    <c:forEach items="${players}" var="player">
                                        <option value="${player.id}" ${goal.assist.id==player.id?'selected':''}>${player}</option>
                                    </c:forEach>
                                </select>
                                </span>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </tag:formField>
            </div>
            </div>
            <div class="form-group" id="buttons">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="submit" type="submit" class="btn btn-default"><spring:message
                            code="button.update"/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<script src="<c:url value='/resources/js/svk-ui-1.5.js'/>"></script>
<%@ include file="../jspf/datePickerDependencies.jspf" %>

<script type="text/javascript">
    (function ($, utils) {
        var isUpdate = "${update}";
        var homeTeam = "${match.homeTeam.name}";
        var defaultTeam = "${defaultTeam}";
        var atGoals = $('#atGoals');
        var htGoals = $('#htGoals');
        var goals = $('#goalsDiv');
        var buttons = $('#buttons');
        var next = $('#next');
        var goalsForm = $("#goalsForm");
        var changeMatchResult = $("#matchResult");
        var matchStatusText = $("#statusText");
        var matchStatus = $("#status");

        statusChanged(matchStatus);

        matchStatus.change(function () {
            statusChanged(matchStatus);
        });

        function getRow(content, order) {
            content += '<span class="space-bottom"><input name="goals[' + order + '].order" class="goal-order" value="' + order + '" style="display: none"/>';
            content = getPlayerSelect(content, order, 'scorer');
            content = getPlayerSelect(content, order, 'assist');
            content += '</span>';
            return content;
        }

        function getPlayerSelect(content, order, type) {
            content += '<select class="form-control" name="goals[' + order + '].' + type + '">';
            if (type === 'assist') {
                content += '<option value=""><spring:message code="text.assist"/></option>';
            }
            else {
                content += '<option value=""><spring:message code="text.scorer"/></option>';
            }
            <c:forEach items="${players}" var="player">
            content += '<option value="${player.id}">${player}</option>';
            </c:forEach>
            content += '</select>';
            return content;
        }

        function updateRows(scorer, assist, orderInput, order) {
            scorer.attr('name', 'goals.formGoal[' + order + '].scorer');
            assist.attr('name', 'goals.formGoal[' + order + '].assist');
            orderInput.attr('name', 'goals.formGoal[' + order + '].order');
            orderInput.val(order);
        }

        function checkInput() {
            return (htGoals.val() == "" || atGoals.val() == "")
        }

        function disableInputs() {
            atGoals.prop('disabled', true);
            htGoals.prop('disabled', true);
        }

        function enableInputs() {
            atGoals.prop('disabled', false);
            htGoals.prop('disabled', false);
        }

        function update(field) {
            goalsForm.empty();
            var content = '';
            var goalsNr;
            goalsNr = field.val();
            for (var i = 0; i < goalsNr; i++) {
                content = getRow(content, i);
            }
            goalsForm.html(content);
            checkEmpty();
        }

        function checkEmpty() {
            ($.trim(goalsForm.html()) === "") ? goals.hide() : goals.show();
        }

        function statusChanged(element) {
            changeMatchResult.hide();
            matchStatusText.hide();

            switch (element.val()) {
                case "CANCELLED":
                    matchStatusText.show();
                    break;
                case "PLAYED":
                    changeMatchResult.show();
                    break;
            }
        }

        $("[class*='date']").datetimepicker({
            pickTime: true,
            pick12HourFormat: false,
            format: "DD/MM/YYYY HH:mm"
        });

        $(document).ready(function () {
            var elementToCheck = homeTeam === defaultTeam ? htGoals : atGoals;
            if (isUpdate === "true") {
                //disableInputs();
                goals.show();
            }
            else {
                enableInputs();
                goals.hide();
            }

            checkEmpty();

            utils.setKeyupAndBlur(elementToCheck, function () {
                update(elementToCheck);
            });

        });
    })(jQuery, svk.utils)


</script>
<%@ include file="../jspf/footer.jspf" %>