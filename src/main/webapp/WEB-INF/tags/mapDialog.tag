<%@tag description="Map dialog" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="dialogId" required="true" type="java.lang.String"%>

<div class="modal fade" id="${dialogId}" tabindex="-1" role="dialog" aria-labelledby="${dialogId}" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header  alert alert-info">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 id="delete-modal-title" class="modal-title"><spring:message code="title.map"/></h4>
            </div>
            <div id="delete-modal-body" style="text-align: center" class="modal-body google-maps">
                <iframe id="mapFrame" scrolling="no" marginheight="0" marginwidth="0" src="" frameborder="0"></iframe>
            </div>
        </div>
    </div>
</div>