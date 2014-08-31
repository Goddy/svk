<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/resultMessage.jspf"%>

<h2><spring:message code="text.userdetails"/></h2>
<div class="panel panel-default">
    <div class="panel-body">
        <form:form action="update_details" modelAttribute="Account" cssClass="form-horizontal">
            <tag:formField path="username" label="label.email" title="label.email" type="input" optional="false"/>
            <tag:formField path="firstName" label="label.firstName" title="label.firstName" type="input" optional="false"/>
            <tag:formField path="lastName" label="label.lastName" title="label.lastName" type="input" optional="false"/>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="btnSubmitDetails" type="submit" class="btn btn-primary"><spring:message code='button.update'/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<h2><spring:message code="text.changepassword"/></h2>
<div id="pwdResult">

</div>
<div class="panel panel-default">
    <div class="panel-body">
        <form:form id="changePassword" modelAttribute="changePassword" cssClass="form-horizontal">
            <tag:formField path="newPassword" label="label.password" title="label.password" type="password" optional="false"/>
            <tag:formField path="confirmPassword" label="label.confirmPassword" title="label.confirmPassword" type="password" optional="false"/>
            <div id="pwdError">

            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="btnSubmitPwd" class="btn btn-primary"><spring:message code='button.update'/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="../jspf/footer.jspf"%>
<script src="<c:url value='/resources/js/svk-1.0.0.js'/>"></script>
<script type="text/javascript">
    (function($, pwd){
        $(document).ready(function() {
            var noMatch = '<spring:message code="error.mismatch.account.password"/>';
            var unknownError = '<spring:message code="error.unknown"/>';
            $( "#btnSubmitPwd" ).click(function(e) {
                e.preventDefault();
                pwd.changePassword($("#changePassword"), [noMatch, unknownError], $("#pwdResult"))
            });
        });
    })(jQuery, svk.updatePassword);
</script>



