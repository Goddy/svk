<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@ include file="../jspf/resultMessage.jspf" %>

<h2><spring:message code="text.userdetails"/></h2>
<div class="panel panel-default">
<form:form action="adminUpdate" modelAttribute="accountProfileForm" cssClass="form-horizontal"
           enctype="multipart/form-data">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <form:hidden path="removeAvatar"/>
        <form:hidden path="avatarUrl"/>
        <form:hidden path="accountId"/>
        <tag:formField path="username" label="label.email" title="label.email" type="input" optional="false"/>
        <tag:formField path="firstName" label="label.firstName" title="label.firstName" type="input"
                       optional="false"/>
        <tag:formField path="lastName" label="label.lastName" title="label.lastName" type="input" optional="false"/>
        <tag:formField cssClass="address" path="address" title="label.street" label="label.street" optional="true"
                       type="input"/>
        <tag:formField cssClass="address" path="postalCode" title="label.postalCode" label="label.postalCode"
                       optional="true" type="input"/>
        <tag:formField cssClass="address" path="city" title="label.city" label="label.city" optional="true"
                       type="input"/>
        <tag:formField path="phone" label="label.phone" title="label.phone" type="input" optional="true"/>
        <tag:formField path="mobilePhone" label="label.mobilePhone" title="label.mobilePhone" type="input"
                       optional="true"/>
        <tag:formField path="position" label="label.position" title="label.position" type="empty" optional="true">
            <form:select id="position" path="position" cssClass="form-control">
                <option value="" selected><spring:message code="text.select.position"/></option>
                </option>
                <c:forEach items="${positions}" var="entry">
                    <form:option value="${entry.key}" label="${entry.value}"/>
                </c:forEach>
            </form:select>
        </tag:formField>
        <tag:formField path="avatar" label="label.avatar" title="label.avatar" type="empty" optional="true">
            <div id="uploadAvatarDiv">
                <div id="kv-avatar-errors" class="center-block alert alert-block alert-danger"
                     style="display:none"></div>
                <div class="kv-avatar center-block" style="width:200px">
                    <input id="avatar" name="avatar" type="file" class="file-loading">
                </div>
            </div>
            <div id="currentAvatarDiv">
                <div class="center-block" style="width: 200px;">
                    <img src="${accountProfileForm.avatarUrl}" class="img-thumbnail"/>

                    <div class="btn-group center-block">
                        <button type="button" id="avatarRemoveBtn" class="btn btn-default" data-toggle="tooltip"
                                data-original-title="<spring:message code="button.remove"/>">
                            <i class="glyphicon glyphicon-trash"></i></button>
                        <button type="button" class="btn btn-default center-block" id="avatarEditBtn"
                                data-toggle="tooltip" data-original-title="<spring:message code="button.edit"/>"><i
                                class="glyphicon glyphicon glyphicon-edit"></i></button>
                    </div>

                </div>
            </div>
            <div id="removeAvatarDiv">
                <div><spring:message code="text.removeAvatar"/></div>
                <div class="btn-group">
                    <button type="button" id="avatarBackRemoveBtn" data-toggle="tooltip"
                            data-original-title="<spring:message code="button.reset"/>" class="btn btn-default">
                        <i class="glyphicon glyphicon-repeat"></i></button>
                </div>
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

<script src="<c:url value='/resources/js/fileinput.min.js'/>"></script>
<script src="<c:url value='/resources/js/fileinput_locale_nl.js'/>"></script>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>
<script type="text/javascript">
    (function ($, profile) {
        var hasAvatar = ${not empty accountProfileForm.avatarUrl ? 'true' : 'false'};
        var lang = "${pageContext.response.locale}";

        profile.initialize(hasAvatar, lang);
    })(jQuery, svk.profile)
</script>
<%@ include file="../jspf/footer.jspf" %>


