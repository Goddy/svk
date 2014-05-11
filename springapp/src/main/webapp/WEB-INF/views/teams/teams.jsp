<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<div class="panel-group" id="accordion">
    <c:forEach items="${seasons}" var="season">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-target="#collapse${season.id}"
                       href="#collapse${season.id}" class="collapsed">
                        <spring:message code="text.season"/> ${season.description}
                    </a>
                </h4>
            </div>
            <div id="collapse${season.id}" class="panel-collapse collapse in">
                <div class="panel-body">
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%@ include file="../jspf/footer.jspf" %>

