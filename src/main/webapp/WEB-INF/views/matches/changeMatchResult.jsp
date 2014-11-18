<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<h2><spring:message code="title.goals" arguments="${match.description}"/></h2>

<div class="panel panel-default">
    <div class="panel-body">
        <form:form id="form" modelAttribute="form" action="changeMatchResult.html" cssClass="form-horizontal">
            <form:hidden path="matchId"/>
            <tag:formField path="htGoals" label="text.htGoals" title="text.htGoals" type="input" optional="false"/>
            <tag:formField path="atGoals" label="text.atGoals" title="text.atGoals" type="input" optional="false"/>
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
                                        <option value="${player.id}" ${goal.scorer.id==player.id?'selected':''}>${player.fullName}</option>
                                    </c:forEach>
                                </select>
                                <select class="form-control" name="goals[${goal.order}].assist">
                                    <option value=""><spring:message code="text.assist"/></option>
                                    </option>
                                    <c:forEach items="${players}" var="player">
                                        <option value="${player.id}" ${goal.assist.id==player.id?'selected':''}>${player.fullName}</option>
                                    </c:forEach>
                                </select>
                                </span>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </tag:formField>
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

<%@ include file="../jspf/footer.jspf" %>
<script src="<c:url value='/resources/js/svk-1.0.1.js'/>"></script>
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
            content += '<option value="${player.id}">${player.fullName}</option>';
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