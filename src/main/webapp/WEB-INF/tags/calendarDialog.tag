<%@tag description="Calendar dialog" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="dialogId" required="true" type="java.lang.String" %>

<div class="modal fade" id="${dialogId}" tabindex="-1" role="dialog" aria-labelledby="${dialogId}" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header  alert alert-default">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 id="delete-modal-title" class="modal-title">
                    <spring:message code="title.export.calendar"/>
                </h4>
            </div>
            <div id="delete-modal-body" class="modal-body">
                <spring:message code="text.export.dialog"/>
            </div>
            <div class="modal-footer">
                <button id="calendar-download-modal-btn" name="download" type="button" class="btn btn btn-info">
                    <spring:message code='button.download'/></button>
                <button name="cancel" type="button" class="btn btn-default" data-dismiss="modal"><spring:message
                        code='button.cancel'/></button>
            </div>
        </div>
    </div>
</div>