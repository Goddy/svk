<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag import='net.tanesha.recaptcha.ReCaptcha' %>
<%@ tag import='net.tanesha.recaptcha.ReCaptchaFactory' %>
<%@ attribute name='privateKey' required='true' %>
<%@ attribute name='publicKey' required='true' %>
<div class="form-group">
    <div class="col-sm-2 col-sm-2 control-label"></div>
    <div class="col-sm-10">
        <div id="captcha_paragraph">
            <%
                ReCaptcha c = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
                out.print(c.createRecaptchaHtml(null, null));
            %>
            <c:if test="${invalidRecaptcha == true}">
          <span class="error">
            <spring:message code="invalid.captcha"/>
          </span>
            </c:if>
        </div>
    </div>
</div>
