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
        <div class="panel-group" id="accordion">
            <tag:faq name="faq1"/>
            <tag:faq name="faq2"/>
            <tag:faq name="faq3"/>
            <tag:faq name="faq4"/>
            <tag:faq name="faq5"/>
        </div>
    </div>
</div>
<%@ include file="jspf/footer.jspf" %>
