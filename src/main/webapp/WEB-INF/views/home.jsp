<%@ include file="jspf/header.jspf" %>
<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><spring:message code="nav.general"/>
        </li>
    </ul>
</div>

<div class="col-md-12">
    <div class="box">
        <h1>
            <spring:message code="title.welcome"/>
        </h1>

        <h2><spring:message code="title.welcome.about"/></h2>
        <spring:message code="text.welcome.about"/>
    </div>
    <div class="box">
        <h2><spring:message code="title.welcome.website"/></h2>
        <spring:message code="text.welcome.website"/>
    </div>
</div>

<%@ include file="jspf/footer.jspf" %>

