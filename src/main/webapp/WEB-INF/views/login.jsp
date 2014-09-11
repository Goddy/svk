<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="login_error" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="jspf/header.jspf" %>
<!--
If the user is anonymous (not logged in), show the login form
and social sign in buttons.
-->
<sec:authorize access="isAnonymous()">

    <!-- Login form -->
    <h2><spring:message code="title.login"/></h2>
    <div class="panel panel-default">
        <div class="panel-body">
            <!--
                Error message is shown if login fails.
            -->
            <c:if test="${param.error eq 'bad_credentials'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="text.login.failed"/>
                </div>
            </c:if>
            <!-- Specifies action and HTTP method -->
            <form action="<c:url value='/j_spring_security_check' />" method="post" name="login_form">
                <!-- Add CSRF token -->

                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4">
                        <label class="control-label" for="j_username"><spring:message code="label.email"/>:</label>
                        <!-- Add username field to the login form -->
                        <input type="text" name="j_username" id="j_username" value="tompels@gmail.com" class="form-control"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4">
                        <label class="control-label" for="j_password"><spring:message                              code="label.password"/>:</label>
                        <!-- Add password field to the login form -->
                        <input type="password" name="j_password" id="j_password" value="P@ssword" class="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-4">
                        <!-- Add submit button -->
                        <button type="submit" class="btn btn-default"><spring:message code="button.login"/></button>
                    </div>
                </div>
            </form>

            <login_error:if test="${'fail' eq param.auth}">
            <div class="row">
                <div class="form-group col-lg-4">
                    <div style="color:red">
                            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
                </div>
            </div>
            </login_error:if>

            <div class="row">
                <div class="form-group col-lg-4">
                    <!-- Add create user account link -->
                    <a href="/account/register.html"><spring:message code="button.register"/></a>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4">
                    <!-- Add create user account link -->
                    <a href="/account/getPwdRecoveryCode.html"><spring:message code="button.fogot.password"/></a>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>
<!--
If the user is already authenticated, show a help message instead
of the login form and social sign in buttons.
-->
<sec:authorize access="isAuthenticated()">
    <p><spring:message code="text.loggedin"/></p>
</sec:authorize>
<%@ include file="jspf/footer.jspf" %>