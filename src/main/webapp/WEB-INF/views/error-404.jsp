<%@ include file="jspf/header.jspf"%>
    <script type="text/javascript">
        $(document).ready(function(){
                setTimeout('redirect()','5000');
        });
        function redirect(){
            window.location = "/news.html";
        }
        
    </script>
<div class="col-md-12">
    <div class="box">
        <h2><spring:message code="error_404_title"/></h2>

        <div class="alert alert-danger">
            <spring:message code="error_404_info"/>
        </div>
    </div>
</div>

<%@ include file="jspf/footer.jspf"%>