<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal" />
<div class="row">
    <div class="col-md-4">
    </div>
    <div class="col-md-4">
    </div>
    <div class="col-md-4">
        <div class="input-group">
            <span class="input-group-addon">
  				<span class="glyphicon glyphicon-search blue"></span>
  			</span>
            <input id="search" type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<h1><p><spring:message code="text.news" /></h1>
<div id="default">
    <c:choose>
        <c:when test="${empty newsList}"><p><spring:message code="text.nomessages"/></p></c:when>
        <c:otherwise>
            <c:forEach var="newsItem" items="${newsList}">
            <div class="news-div">
                <tag:news newsItem="${newsItem}"/>
                <tag:comment newsItem="${newsItem}"/>
            </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
</div>
<div id="loader" class="text-center"></div>
<div id="searchResult" style="display: none;"></div>
<%@ include file="../jspf/footer.jspf" %>
<tag:deleteDialog dialogId="delete-modal"/>

<script type="text/javascript">

    (function ($, news) {
        var newsFunctions = news.initCommentFunctions(
                ["<spring:message code="text.delete.comment.title"/>", "<spring:message code="text.delete.comment"/>"],
                ["<spring:message code="text.delete.news.title"/>", "<spring:message code="text.delete.news"/>"]
        );
        var searchFunctions = news.initSearchFunctions(["<spring:message code='text.noResults'/>"]);
        newsFunctions.updateButtons();
    })(jQuery, svk.news);
</script>

