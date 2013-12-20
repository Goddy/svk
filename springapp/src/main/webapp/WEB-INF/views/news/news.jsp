<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp"%>
<h1>Articles</h1>
<c:choose>
    <c:when test="${empty newsList}"><p>None</p></c:when>
    <c:otherwise>
        <c:forEach var="newsItem" items="${newsList}">
            <div><c:out value="${newsItem.header}" /></div>
            <div><c:out value="${newsItem.account.getFullName()}" /></div>
            <div><fmt:formatDate value="${newsItem.postDate}" /></div>
        </c:forEach>
    </c:otherwise>
</c:choose>
<%@ include file="../footer.jsp"%>
