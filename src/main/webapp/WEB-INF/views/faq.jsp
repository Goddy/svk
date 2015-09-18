<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="jspf/header.jspf" %>
<h1>
    <spring:message code="title.faq"/>
</h1>

<tag:faq question="title.faq1" answer="text.faq1"/>
<tag:faq question="title.faq2" answer="text.faq2"/>
<tag:faq question="title.faq3" answer="text.faq3"/>
<tag:faq question="title.faq4" answer="text.faq4"/>
<tag:faq question="title.faq5" answer="text.faq5"/>

<script src="<c:url value='/resources/js/svk-ui-1.3.js'/>"></script>
<script type="text/javascript">
    (function ($, faq) {
        faq.initialize();
    })(jQuery, svk.faq)
</script>

<%@ include file="jspf/footer.jspf" %>
