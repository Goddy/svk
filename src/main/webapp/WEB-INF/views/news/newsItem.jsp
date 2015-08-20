<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../jspf/header.jspf" %>
<div class="news-div">
    <%@ include file="../jspf/newsItem.jsp" %>
</div>
<%@ include file="../jspf/footer.jspf" %>

<tag:deleteDialog dialogId="delete-modal"/>

<script type="text/javascript">

    (function ($, news, utils) {
        var newsFunctions = news.initCommentFunctions(
                ["<spring:message code="text.delete.comment.title"/>", "<spring:message code="text.delete.comment"/>"],
                ["<spring:message code="text.delete.news.title"/>", "<spring:message code="text.delete.news"/>"]
        );
        news.initSearchFunctions(["<spring:message code='text.noResults'/>"]);
        newsFunctions.updateButtons();

        var commentDiv = $(document).find("div[id^='comments_']");
        utils.toggleAndFocus(commentDiv);

    })(jQuery, svk.news, svk.utils);
</script>


