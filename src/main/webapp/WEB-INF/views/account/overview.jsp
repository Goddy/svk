<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><spring:message code="nav.accounts"/>
        </li>
    </ul>
</div>

<div class="col-md-12" id="accordion">
    <%@ include file="../jspf/resultMessage.jspf" %>
    <div class="box">
        <table class="table table-hover rwd-table">
            <tr>
                <th><spring:message code="label.firstName"/></th>
                <th><spring:message code="label.lastName"/></th>
                <th><spring:message code="label.email"/></th>
                <th><spring:message code="text.actions"/></th>
            </tr>
            <c:forEach items="${accounts}" var="a">
                <tr>
                    <td>
                        <div>${a.firstName}</div>
                    </td>
                    <td>
                        <div>${a.lastName}</div>
                    </td>
                    <td>
                        <div>${a.username}</div>
                    </td>
                    <td data-th="<spring:message code="text.actions"/>">
                        <a href="editProfile.html?id=${a.id}" class="btn btn-default">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>

