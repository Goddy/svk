<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@ include file="../jspf/resultMessage.jspf" %>

<h2><spring:message code="text.userdetails"/></h2>
<div class="panel panel-default">
<form:form action="update_details" modelAttribute="accountProfileForm" cssClass="form-horizontal"
           enctype="multipart/form-data">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <tag:formField path="username" label="label.email" title="label.email" type="input" optional="false"/>
        <tag:formField path="firstName" label="label.firstName" title="label.firstName" type="input"
                       optional="false"/>
        <tag:formField path="lastName" label="label.lastName" title="label.lastName" type="input" optional="false"/>
        <tag:formField path="phone" label="label.phone" title="label.phone" type="input" optional="true"/>
        <tag:formField path="mobilePhone" label="label.mobilePhone" title="label.mobilePhone" type="input"
                       optional="true"/>
        <tag:formField path="avatar" label="label.avatar" title="label.avatar" type="empty" optional="true">
            <div class="kv-avatar center-block" style="width:200px">
                <input id="avatar" name="avatar" type="file" class="file-loading">
            </div>
        </tag:formField>
        <tag:formField path="doodleNotificationMails" label="label.doodleNotificationMails"
                       title="label.doodleNotificationMails" type="checkbox" optional="false"/>
        <tag:formField path="newsNotificationMails" label="label.newsNotificationMails"
                       title="label.newsNotificationMails" type="checkbox" optional="false"/>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btnSubmitDetails" type="submit" class="btn btn-primary"><spring:message
                        code='button.update'/></button>
            </div>
        </div>
    </div>
    </div>
</form:form>

<c:choose>
    <c:when test="${!Account.hasSignInProvider}">
        <h2><spring:message code="title.social.profiles"/></h2>

        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row social-button-row">
                    <div class="col-lg-4">
                        <!-- Add Facebook connect in button -->
                        <form action="<c:url value="${pageContext.request.contextPath}/connect/facebook" />"
                              method="POST">
                            <button class="btn btn-facebook"><i class="fa fa-facebook"></i> | <spring:message
                                    code="button.social.signin.add"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <%-- it will not be allowed to remove connections
    <c:otherwise>
        <div class="col-lg-4">
            <!-- Add Facebook connect in button -->
            <form action="<c:url value="${pageContext.request.contextPath}/connect/facebook" />" method="POST">
                <input type="hidden" name="_method" value="DELETE">
                <p><spring:message code="text.social.signin.delete"/></p>
                <button class="btn btn-facebook"><i class="fa fa-facebook"></i> | <spring:message code="button.social.signin.delete"/></button>
            </form>
        </div>
    </c:otherwise>
    --%>
</c:choose>

<h2><spring:message code="text.changepassword"/></h2>

<div class="panel panel-default">
    <div class="panel-body">
        <c:choose>
            <c:when test="${Account.hasPassword == true}">
                <form:form id="changePassword" modelAttribute="changePassword" cssClass="form-horizontal"
                           action="changePassword">
                    <tag:formField path="oldPassword" label="label.currentPwd" title="label.currentPwd" type="password"
                                   optional="false"/>
                    <tag:formField path="newPassword" label="label.password" title="title.password" type="password"
                                   optional="false"/>
                    <tag:formField path="confirmPassword" label="label.confirmPassword" title="label.confirmPassword"
                                   type="password" optional="false"/>
                    <div id="pwdError">

                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button id="btnSubmitPwd" class="btn btn-primary"><spring:message
                                    code='button.change'/></button>
                        </div>
                    </div>
                </form:form>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning" role="alert"><spring:message code="text.social.setPassword"/></div>
                <form:form id="changePassword" modelAttribute="changePassword" cssClass="form-horizontal"
                           action="setPassword">
                    <form:hidden path="oldPassword"/>
                    <tag:formField path="newPassword" label="label.password" title="title.password" type="password"
                                   optional="false"/>
                    <tag:formField path="confirmPassword" label="label.confirmPassword" title="label.confirmPassword"
                                   type="password" optional="false"/>
                    <div id="pwdError">

                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button id="btnSubmitSetPwd" class="btn btn-primary"><spring:message
                                    code='button.set'/></button>
                        </div>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script src="<c:url value='/resources/js/fileinput.min.js'/>"></script>
<script src="<c:url value='/resources/js/fileinput_locale_nl.js'/>"></script>
<script type="text/javascript">
    (function ($) {
        $("#avatar").fileinput({
            overwriteInitial: true,
            maxFileSize: 150,
            showClose: false,
            showCaption: false,
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors',
            msgErrorClass: 'alert alert-block alert-danger',
            defaultPreviewContent: '<img src="http://placehold.it/100x100" alt="Your Avatar" style="width:100px">',
            layoutTemplates: {main2: '{preview} {remove} {browse}'},
            allowedFileExtensions: ["jpg", "png", "gif"]
        });
    })(jQuery)
</script>
<%@ include file="../jspf/footer.jspf" %>


