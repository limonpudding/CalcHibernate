function slidePrev() {
    $('#carouselExampleControls').carousel('prev');
}

function slideNext() {
    $('#carouselExampleControls').carousel('next');
}

var answerApp = angular.module("answerApp", []);
answerApp.controller("answerController", function ($scope, $http) {
    $scope.idSession;
    $scope.sessionsSort = {
        mode: 'id',
        direction: 'asc'
    };
    $scope.operationsSort = {
        mode: 'operationKind',
        direction: 'asc'
    };
    $scope.openSecondTable = function (id) {
        $scope.idSession = id;
        $scope.loadSecond();
        slideNext();
    };
    $scope.loadFirst = function () {
        $http({
            method: 'GET',
            url: "rest/tables?table=1&mode=" + $scope.sessionsSort.mode + "&order=" + $scope.sessionsSort.direction
        }).then(function success(response) {
            $scope.firstTable = response.data;
        });
    };
    $scope.loadSecond = function () {
        $http({
            method: 'GET',
            url: "rest/tables?table=2&mode=" + $scope.operationsSort.mode + "&order=" + $scope.operationsSort.direction + "&id=" + $scope.idSession
        }).then(function success(response) {
                $scope.secondTable = response.data;
            }
        );
    };
    $scope.loadFirst();
});

