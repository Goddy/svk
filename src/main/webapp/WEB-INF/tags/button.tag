<
<%@tag description="Twitter bootstrap buttons" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="text" required="true" type="java.lang.String" %>
<%@attribute name="cssClass" required="true" type="java.lang.String" %>

<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <button id="${id}" class="${cssClass}"><spring:message code="${text}"/></button>
    </div>
</div>