<%@tag description="Faq component" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="question" required="true" type="java.lang.String" %>
<%@attribute name="answer" required="true" type="java.lang.String" %>

<div>
    <a href="#" class="faq"><spring:message code="${question}"/></a>

    <p class="text-muted">
        <spring:message code="${answer}"/>
    </p>
</div>