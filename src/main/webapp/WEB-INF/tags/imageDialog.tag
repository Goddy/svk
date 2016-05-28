<%@tag description="Image upload dialog" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@attribute name="dialogId" required="true" type="java.lang.String"%>

<div class="modal fade" id="${dialogId}" tabindex="-1" role="dialog" aria-labelledby="${dialogId}" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header  alert alert-danger">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 id="upload-image-modal-title" class="modal-title"></h4>
            </div>
            <div id="upload-image-modal-body" class="modal-body">
                <form enctype="multipart/form-data" id="imageUploadForm">
                    <label class="control-label"><spring:message code='button.select'/></label>
                    <input id="image" name="image" type="file" class="file">
                </form>
            </div>
            <div class="modal-footer">
                <button id="upload-image-modal-btn" name="upload" type="button" class="btn btn btn-info"><spring:message code='button.add'/></button>
                <button name="cancel" type="button" class="btn btn-default" data-dismiss="modal"><spring:message code='button.cancel'/></button>
            </div>
        </div>
    </div>
</div>