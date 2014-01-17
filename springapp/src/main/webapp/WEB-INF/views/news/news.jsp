<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp"%>
<h1><p><spring:message code="text.news" /></h1>
<table>
    <tr>
        <form action="/news/search.html" method="post" name="search_form">
        <td><spring:message code="label.search" /></td>
        <td><input id="search" name="search" type="text"/></td>
        <td><input type="submit" name="submit_search" value="<spring:message code="button.search" />" /></td>
        </form>
    </tr>
</table>


<c:choose>
    <c:when test="${empty newsList}"><p><spring:message code="text.nomessages" /></p></c:when>
    <c:otherwise>
        <c:forEach var="newsItem" items="${newsList}">
            <div>
            <h2><c:out value="${newsItem.header}" /></h2>
            <p align="left"><c:out value="${newsItem.content}" /></p>
            <p align="right"><spring:message code="text.postedby" /> <c:out value="${newsItem.account.getFullName()}" /> <spring:message code="text.on" /> <fmt:formatDate value="${newsItem.postDate}" /> | <a class="tooltip" href="/news/edit.html?newsid=<c:out value="${newsItem.getId()}" />'"><img src="<c:url value='/resources/images/pencil.png'/>"/><span><spring:message code="text.edit" /></span></a> | <a  class="tooltip" href="/news/delete.html?newsid=<c:out value="${newsItem.getId()}" />"><img src="<c:url value='/resources/images/cancel.png'/>"/><span><spring:message code="text.remove" /></span></a></p>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>

<c:choose>
<c:when test="${not single}"></c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
<p style="text-align:center"><a href="<c:out value="${first}" />" > << <spring:message code="text.first" /></a> | <a href="<c:out value="${previous}" />" ><spring:message code="text.previous" /></a> | <a href="<c:out value="${next}" />"><spring:message code="text.next" /></a> | <a href="<c:out value="${last}" />" ><spring:message code="text.last" /> >> </a> </p>
<%@ include file="../footer.jsp"%>
