<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="loading" pageEncoding="UTF-8" %>

<div ng-show="loading" class="ajax-loading">
    <img id="ajax-loading-image" src="<c:url value='/resources/images/loading.gif'/>" alt="Loading..."/>
</div>