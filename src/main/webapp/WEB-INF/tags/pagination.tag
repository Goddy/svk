<%@tag description="Pagination" pageEncoding="UTF-8" %>

<div class="text-center">
    <ul class="pagination blue">
        <li>
            <a ng-click="getPage(currentPage - 1)" ng-if="hasPrevious">
                &laquo;
            </a>
        </li>
        <li ng-repeat="n in [].constructor(totalPages) track by $index" ng-class="{'active': $index==currentPage}">
            <a ng-click="getPage($index)">
                {{$index + 1}}
            </a>
        </li>
        <li>
            <a ng-click="getPage(currentPage + 1)" ng-if="hasNext">
                &raquo;
            </a>
        </li>
    </ul>
</div>