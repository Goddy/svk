<%@tag description="Faq component" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="name" required="true" type="java.lang.String" %>


<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#${name}" class="">
                <spring:message code="title.${name}"/>
            </a>
        </h4>
    </div>
    <div id="${name}" class="panel-collapse collapse in">
        <div class="panel-body">
            <p class="text-muted">
                <spring:message code="text.${name}"/>
            </p>
        </div>
    </div>
</div>