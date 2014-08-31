<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../jspf/header.jspf" %>
<div class="panel panel-info">
    <div class="panel-heading"><c:out value="${newsItem.header}"/></div>
    <div class="panel-body">
        <p align="left"><c:out value="${newsItem.content}"/></p>

        <div style="text-align: right"><spring:message code="text.postedby"/> <c:out
                value="${newsItem.account.fullName}"/> <spring:message code="text.on"/>
            <fmt:formatDate value="${newsItem.postDate}"/>
            <sec:authorize access="hasRole('ADMIN')">
                <div class="btn-group">
                    <a href="/news/editNews.html?id=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-edit edit"><span class=""></span></a>
                    <a href="/news/deleteNews.html?id=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-trash delete"><span class="delete"></span></a>
                </div>
            </sec:authorize>
        </div>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>

