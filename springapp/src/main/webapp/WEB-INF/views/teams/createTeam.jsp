<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<h2><spring:message code="title.createTeam"/> </h2>
        <form:form action="createTeam.html" cssClass="form-horizontal" role="form" modelAttribute="form">
            <tag:formField path="teamName" title="label.teamName" label="label.teamName" optional="false" type="input"/>
            <tag:formField cssClass="address" path="address" title="label.street" label="label.street" optional="false"
                           type="input"/>
            <tag:formField cssClass="address" path="postalCode" title="label.postalCode" label="label.postalCode"
                           optional="false" type="input"/>
            <tag:formField cssClass="address" path="city" title="label.city" label="label.city" optional="false" type="input"/>
            <tag:formField path="googleLink" title="label.googleLink" label="label.googleLink" optional="true" type="input"/>
            <tag:formField path="useLink" label="label.useLink" title="label.useLink" type="checkbox" optional="true"/>
            <div id="map" class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <iframe id="mapFrame" width="425" height="350" frameborder="0" scrolling="no" marginheight="0"
                            marginwidth="0" src=""></iframe>
                    <br/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default"><spring:message code="button.add"/></button>
                </div>
            </div>
        </form:form>

<%@ include file="../jspf/footer.jspf" %>
<script type="text/javascript">
    var iFrame = $('#mapFrame');
    var mapDiv = $('#map');
    var googleLinkInput = $('#googleLink');
    var address = $('#address');
    var city = $('#city');
    var postalCode = $('#postalCode');

    $(function () {
        mapDiv.hide();
        googleLinkInput.prop('disabled', true);
        checkGoogleLinkInput();

        $('.address').keyup(function () {
            checkGoogleLinkInput();
        });
    });

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
