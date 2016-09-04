<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal" />
<div class="row">
    <div class="col-md-12">
        <ul class="breadcrumb">
            <li><spring:message code="nav.home"/>
            </li>
        </ul>
    </div>
</div>
<div class="row m-b-1">
    <div class="col-md-4 col-md-offset-8">
        <div class="input-group">
            <span class="input-group-addon">
  				<span class="glyphicon glyphicon-search"></span>
  			</span>
            <input id="search" type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<div class="row m-t-1">
    <div class="col-md-12">
        <div id="blog-homepage">
            <div id="default">
                <c:choose>
                    <c:when test="${empty newsList}"><p><spring:message code="text.nomessages"/></p></c:when>
                    <c:otherwise>
                        <c:forEach var="newsItem" items="${newsList}">
                            <div class="news-div">
                                <tag:news newsItem="${newsItem}">
                                    <hr/>
                                    <tag:comment newsItem="${newsItem}"/>
                                </tag:news>

                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                <tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
            </div>
            <div id="loader" class="text-center"></div>
            <div id="searchResult" style="display: none;"></div>
        </div>
</div>
</div>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<tag:deleteDialog dialogId="delete-modal"/>

<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<script type="text/javascript">

    (function ($, news) {
        var newsFunctions = news.initCommentFunctions(
                ["<spring:message code="text.delete.comment.title"/>", "<spring:message code="text.delete.comment"/>"],
                ["<spring:message code="text.delete.news.title"/>", "<spring:message code="text.delete.news"/>"]
        );
        var searchFunctions = news.initSearchFunctions(["<div class=\"box\"><spring:message code='text.noResults'/></div>"]);
        newsFunctions.updateButtons();
    })(jQuery, svk.news);
</script>
<%@ include file="../jspf/footer.jspf" %>

