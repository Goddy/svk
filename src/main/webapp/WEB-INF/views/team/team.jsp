<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<div class="row">
    <div class="col-md-12">
    <div id="carousel-team" class="carousel slide" data-ride="carousel">
        <!-- Indicators
        <ol class="carousel-indicators">
            <li data-target="#carousel-team" data-slide-to="0" class="active"></li>
        </ol> -->

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active thumbnail">
                <img src="http://res.cloudinary.com/dtwkkwtee/image/upload/v1449832072/public/team.jpg">
                <div class="carousel-caption">
                </div>
            </div>
        </div>
    </div>
    </div>
</div>

<tag:position title="title.goalkeepers" players="${players['GOALKEEPER']}"/>
<tag:position title="title.defenders" players="${players['DEFENDER']}"/>
<tag:position title="title.midfielders" players="${players['MIDFIELDER']}"/>
<tag:position title="title.forwards" players="${players['FORWARD']}"/>
<tag:position title="title.unknown" players="${players['UNKNOWN']}"/>


<script src="<c:url value='/resources/js/svk-ui-1.3.js'/>"></script>

<script type="text/javascript">
    (function ($) {
        var teamItem = $(".avatar");
        var height = 0;
        teamItem.each(function () {
            if ($(this).height() > height) {
                height = $(this).height();
            }
        });

        teamItem.height(height);
    })(jQuery);
</script>
<%@ include file="../jspf/footer.jspf" %>