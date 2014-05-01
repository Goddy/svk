<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../jspf/header.jspf" %>
<div class="row">
    <div class="col-md-6">
        <div class="input-group">
            <span class="input-group-addon glyphicon glyphicon-search blue"></span>
            <input type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<h1><p><spring:message code="text.news" /></h1>
<c:choose>
    <c:when test="${empty newsList}"><p><spring:message code="text.nomessages" /></p></c:when>
    <c:otherwise>
        <c:forEach var="newsItem" items="${newsList}">
            <div class="panel panel-info">
                <div class="panel-heading"><c:out value="${newsItem.header}"/></div>
                <div class="panel-body">
                    <p align="left"><c:out value="${newsItem.content}"/></p>

                    <p align="right"><spring:message code="text.postedby"/> <c:out
                            value="${newsItem.account.getFullName()}"/> <spring:message code="text.on"/> <fmt:formatDate
                            value="${newsItem.postDate}"/> | <a class="tooltip"
                                                                href="/news/edit.html?newsid=<c:out value="${newsItem.getId()}" />'"><img
                            src="<c:url value='/resources/images/pencil.png'/>"/><span><spring:message
                            code="text.edit"/></span></a> | <a class="tooltip"
                                                               href="/news/delete.html?newsid=<c:out value="${newsItem.getId()}" />"><img
                            src="<c:url value='/resources/images/cancel.png'/>"/><span><spring:message
                            code="text.remove"/></span></a></p>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>

<c:choose>
<c:when test="${not single}"></c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
<div class="text-center">
    <ul class="pagination blue">
        <li><a href="<c:out value="${first}" />">&laquo;&nbsp;<spring:message code="text.first"/></a></li>
        <li><a href="<c:out value="${previous}" />"><spring:message code="text.previous"/></a></li>
        <li><a href="<c:out value="${next}" />"><spring:message code="text.next"/></a></li>
        <li><a href="<c:out value="${last}" />"><spring:message code="text.last"/>&nbsp;&raquo;</a></li>
    </ul>
</div>

<%@ include file="../footer.jsp"%>

