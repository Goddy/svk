<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<link href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
<!-- Le script -->
<script type="text/javascript" src="/resources/js/jquery.pagedown-bootstrap.combined.min.js"></script>
<script type="text/javascript" src="/resources/js/he.js"></script>
<script type="text/javascript" src="/resources/js/to-markdown.js"></script>

<c:set var="update" value="${command == 'updateNews.html' ? true : false }"/>

<div class="panel panel-default">
    <div class="panel-body">
        <form:form modelAttribute="form" action="${command}" cssClass="form-horizontal">
        <form:hidden path="id"/>
        <form:hidden path="body"/>
        <tag:formField path="title" label="label.title" title="title.title" type="input" optional="false"/>
            <c:if test="${!update}">
                <tag:formField path="sendEmail" label="label.news.sendEmail" title="label.news.sendEmail"
                               type="checkbox" optional="false"/>
            </c:if>
            <div data-toggle="tooltip" class="form-group ${status.error ? 'has-error has-feedback' : '' }"
                 title="<spring:message code='label.body'/>">
                <label class="col-sm-2 col-sm-2 control-label" for="pagedownMe">
                    <spring:message code="label.body"/>
                </label>
                <div class="col-sm-10">
                    <textarea id="pagedownMe"  class="form-control" rows="10">
                    </textarea>
                </div>
             </div>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
                <button id="btnReset" type="reset" class="btn btn-info"><spring:message code='button.reset'/></button>
                <a id="btnCancel" class="btn btn-default" href="news.html"><spring:message code='button.cancel'/></a>
            </div>
        </div>

        </form:form>
        </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var body  = $("#body").val();
        var pageDownMe =  $("textarea#pagedownMe");

        pageDownMe.val('');

        if (body !== '') {
            var bodyText = toMarkdown(body);
            pageDownMe.val(bodyText);
        }

        pageDownMe.pagedownBootstrap();

        var pageDownInput = $("textarea#wmd-input-0");
        var preview = $("#wmd-preview-0");

        setWellClass();

        pageDownInput.on("change keyup paste", function() {
            $("#body").val(preview.html());
            setWellClass();
        });

        function setWellClass() {
            if ($.trim(pageDownInput.val()) !== "") {
                preview.addClass("well");
            }
            else {
                preview.removeClass("well")
            }
        }
    });
</script>
<%@ include file="../jspf/footer.jspf" %>