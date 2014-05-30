<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>


<form:form method="post" action="changeMatchResult.html" cssClass="form-horizontal">
    <div class="form-group col-sm-10" title="">
        <div class="alert alert-info"><spring:message code="text.matchResult.result"/></div>
        <div class="form-group row">
            <div class="col-md-2">
                <form:input path="htGoals"/>

            </div>
            <div class="col-md-1">
                versus
            </div>

            <div class="col-md-2">
                <input type="text" name="atGoals" id="atGoals" class="form-control"
                       placeholder="${match.awayTeam.name}">
            </div>
            <div class="col-md-2">
                <button type="button" onclick="nextClick();" id="next" class="btn btn-default"><spring:message
                        code="button.next"/></button>
            </div>
        </div>

    </div>

    <div id="goalsDiv">
        <div class="form-group">
            <div class="col-sm-10 ">
                <div class="form-group row" id="goalsForm">
                    <c:choose>
                        <c:when test="${not empty match.goals}">
                            <c:forEach items="${match.goals}" var="goal">
                                <input name="goals[${goal.order}]" style="display: none" value="${goal.order}"/>

                                <div class="col-md-4">
                                    <label class="col-md-2 control-label"><spring:message code="text.scorer"/></label>
                                    <select class="form-control" name="goals[${goal.order}].scorer">
                                        <c:forEach items="${players}" var="player">
                                            <option value="${player.id}" ${goal.scorer.id==player.id?'selected':''}>${player.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label"><spring:message code="text.assist"/></label>

                                <div class="col-md-4">
                                    <select class="form-control" name="goals[${goal.order}].assist">
                                        <c:forEach items="${players}" var="player">
                                            <option value="${player.id}" ${goal.assist.id==player.id?'selected':''}>${player.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </c:forEach>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="form-group" id="buttons">
            <div class="col-sm-offset-4 col-sm-10">
                <button type="button" onclick="backClick()" class="btn btn-danger"><spring:message
                        code="button.back"/></button>
                <button type="submit" class="btn btn-default"><spring:message code="button.add"/></button>
            </div>
        </div>
    </div>
</form:form>

<%@ include file="../jspf/footer.jspf" %>

<script type="text/javascript">
    var isUpdate = "${update}";
    var homeTeam = "${match.homeTeam.name}";
    var defaultTeam = "${defaultTeam}";
    var atGoals = $('#atGoals');
    var htGoals = $('#htGoals');
    var goals = $('#goalsDiv');
    var buttons = $('#buttons');
    var next = $('#next');

    function getRow(content, order) {
        content += '<div class="form-group row"><input name="goal[' + order + '].order" class="goal-order" value="' + order + '" style="display: none"/>' +
                '<label class="col-md-2 control-label"><spring:message code="text.scorer"/></label>' +
                '<div class="col-md-4">';
        content = getPlayerSelect(content, order, 'scorer');
        content += '</div>' +
                '<label class="col-md-1 control-label"><spring:message code="text.assist"/></label>' +
                '<div class="col-md-4">';
        content = getPlayerSelect(content, order, 'assist');
        content += '</div></div>';
        return content;
    }

    function getPlayerSelect(content, order, type) {
        content += '<select class="form-control" name="goal[' + order + '].' + type + '">';
        <c:forEach items="${players}" var="player">
        content += '<option value="${player.id}">${player.fullName}</option>';
        </c:forEach>
        content += '</select>';
        return content;
    }

    function updateRows(scorer, assist, orderInput, order) {
        scorer.attr('name', 'goals[' + order + '].scorer');
        assist.attr('name', 'goals[' + order + '].assist');
        orderInput.attr('name', 'goals[' + order + ']');
        orderInput.val(order);
    }

    function backClick() {
        enableInputs();
        hide(goals);
        show(next);
    }
    function nextClick() {
        if (!checkInput()) {
            update();
            show(goals);
            hide(next);
            disableInputs();
        }
    }

    function checkInput() {
        return (htGoals.val() == "" || atGoals.val() == "")
    }

    function show(element) {
        element.show();
    }

    function hide(element) {
        element.hide();
    }

    function disableInputs() {
        atGoals.prop('disabled', true);
        htGoals.prop('disabled', true);
    }

    function enableInputs() {
        atGoals.prop('disabled', false);
        htGoals.prop('disabled', false);
    }

    function update() {
        var goalsForm = $("#goalsForm");
        goalsForm.empty();
        var content = '';
        var goalsNr;
        goalsNr = homeTeam == defaultTeam ? $('#htGoals').val() : $('#atGoals').val();
        for (var i = 0; i < goalsNr; i++) {
            content = getRow(content, i);
        }
        goalsForm.html(content);
    }

    $(document).ready(function () {
        if (update === true) {
            disableInputs();
            show(goals);
        }
        else {
            enableInputs();
            hide(goals);
        }


    });


</script>
