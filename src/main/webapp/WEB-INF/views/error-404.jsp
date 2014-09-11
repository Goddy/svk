<%@ include file="jspf/header.jspf"%>
    <script type="text/javascript">
        $(document).ready(function(){
                setTimeout('redirect()','5000');
        });
        function redirect(){
            window.location="/home.html";
        }
        
    </script>
<h2><spring:message code="error_404_title" /></h2>
<div class="alert alert-danger">
    <spring:message code="error_404_info" />
</div>
<%@ include file="jspf/footer.jspf"%>