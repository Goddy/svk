<%@ include file="jspf/header.jspf" %>
<div class="col-md-12">
   <div class="box">
      <h1>
         <spring:message code="error.error"/>
      </h1>

      <div class="alert alert-danger" role="alert"><spring:message code="${message}"/></div>
   </div>
</div>

<%@ include file="jspf/footer.jspf" %>
