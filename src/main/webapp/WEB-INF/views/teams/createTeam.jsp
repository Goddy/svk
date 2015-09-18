<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<h2>${title}</h2>
<%@ include file="../jspf/resultMessage.jspf" %>
<div class="panel panel-default">
    <div class="panel-body">
        <form:form action="${action}.html" cssClass="form-horizontal" role="form" modelAttribute="form">
            <form:hidden path="id"/>
            <tag:formField path="teamName" title="label.teamName" label="label.teamName" optional="false" type="input"/>
            <tag:formField path="useExistingAddress" label="label.useExistingAddress" title="label.useExistingAddress" type="checkbox" optional="false"/>
            <div id="existingAddress">
            <tag:formField path="addressId" label="label.address" title="label.address" type="empty" optional="false">
                <select class="form-control" name="addressId">
                    <c:forEach items="${addresses}" var="address">
                        <option value="${address.id}" ${address.id==selectedAddress ?'selected':''}>${address}</option>
                    </c:forEach>
                </select>
            </tag:formField>
            </div>
            <div id="newAddress" style="display: none">
            <tag:formField cssClass="address" path="address" title="label.street" label="label.street" optional="false"
                           type="input"/>
            <tag:formField cssClass="address" path="postalCode" title="label.postalCode" label="label.postalCode"
                           optional="false" type="input"/>
            <tag:formField cssClass="address" path="city" title="label.city" label="label.city" optional="false" type="input"/>
            <tag:formField path="googleLink" title="label.googleLink" label="label.googleLink" optional="true" type="input"/>
            <tag:formField path="useLink" label="label.useLink" title="label.useLink" type="checkbox" optional="true"/>

            <div id="map" class="form-group">
                <div class="col-sm-offset-2 col-sm-10 google-maps">
                    <iframe id="mapFrame" frameborder="0" scrolling="no" marginheight="0"
                            marginwidth="0" src=""></iframe>
                    <br/>
                </div>
            </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${action == 'createTeam'}">
                            <button id="submit" type="submit" class="btn btn-primary"><spring:message code="button.add"/></button>
                        </c:when>
                        <c:otherwise>
                            <button id="submit" type="submit" class="btn btn-primary"><spring:message code="button.update"/></button>
                        </c:otherwise>
                    </c:choose>

                    <a id="btnCancel" class="btn btn-default" href="teams.html"><spring:message code='button.cancel'/></a>

                </div>
            </div>
        </form:form>
    </div>
</div>


<script type="text/javascript">
    var iFrame = $('#mapFrame');
    var mapDiv = $('#map');
    var googleLinkInput = $('#googleLink');
    var address = $('#address');
    var city = $('#city');
    var postalCode = $('#postalCode');
    var submit = $("#submit");
    var existingAddress = $("#existingAddress");
    var newAddress = $("#newAddress");
    var existingAddressCheckbox = $( "input[name*='useExistingAddress']");

    $(function () {
        mapDiv.hide();
        googleLinkInput.prop('disabled', true);
        checkGoogleLinkInput();
        switchAddress(existingAddressCheckbox);

        $('.address').keyup(function () {
            checkGoogleLinkInput();
        });

        existingAddressCheckbox.change(function () {
            switchAddress($(this));
        });

        submit.click(function() {
            googleLinkInput.prop('disabled', false);
        });
    });

    function switchAddress(element) {
        if (element.is(':checked')) {
            existingAddress.show();
            newAddress.hide();
        }
        else {
            existingAddress.hide();
            newAddress.show();
        }
    }

    function checkGoogleLinkInput() {
        if (address.val() !== "" && postalCode.val() !== "" && city.val() !== "") {
            getAddress();
        }
        else {
            mapDiv.hide();
        }
    }

    function getAddress() {
        console.log("Getting address from input");
        var url = 'https://maps.google.be/maps?q=';
        var address = $('#address').val() + ', +' + $('#postalCode').val() + '+' + $('#city').val();
        var link = url + address;
        setAddress(link);
    }

    function setAddress(url) {
        console.log("Setting address");
        var newUrl = url + '&output=embed';
        iFrame.attr('src', newUrl);
        googleLinkInput.attr('value', newUrl);
        mapDiv.show();
    }


</script>
<%@ include file="../jspf/footer.jspf" %>