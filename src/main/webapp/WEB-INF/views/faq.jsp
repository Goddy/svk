<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="jspf/header.jspf" %>

<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><spring:message code="title.faq"/>
        </li>
    </ul>
</div>

<div class="col-md-12">
    <div class="box">
        <tag:faq question="title.faq1" answer="text.faq1"/>
        <tag:faq question="title.faq2" answer="text.faq2"/>
        <tag:faq question="title.faq3" answer="text.faq3"/>
        <tag:faq question="title.faq4" answer="text.faq4"/>
        <tag:faq question="title.faq5" answer="text.faq5"/>
    </div>
</div>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>
<script type="text/javascript">
    (function ($, faq) {
        faq.initialize();
    })(jQuery, svk.faq)
</script>

<%@ include file="jspf/footer.jspf" %>
