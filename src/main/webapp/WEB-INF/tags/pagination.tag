<%@tag description="Pagination" pageEncoding="UTF-8" %>

<div class="text-center" ng-show="page.totalPages > 1">
    <ul class="pagination blue">
        <li>
            <a ng-click="getPage(currentPage - 1, searchTerm)" ng-if="page.hasPrevious">
                &laquo;
            </a>
        </li>
        <li>
            <a>
                {{currentPage + 1}}
            </a>
        </li>
        <li>
            <a ng-click="getPage(currentPage + 1, searchTerm)" ng-if="page.hasNext">
                &raquo;
            </a>
        </li>
    </ul>
</div>