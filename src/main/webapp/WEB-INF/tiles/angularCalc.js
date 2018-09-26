var answerApp = angular.module("answerApp", []);
answerApp.controller("answerController", function ($scope, $http) {
    $scope.operationName = '';
    $scope.buttonDisplay = false;
    $scope.aDisplay = 'none';
    $scope.bDisplay = 'none';
    $scope.label1Display = 'none';
    $scope.label2Display = 'none';
    $scope.calc = {
        a: '',
        b: '',
        operation: 'sum'
    };
    $scope.calculate = function (kind, name) {
        $scope.visibleInterface();
        $scope.operationName = name;
        if (kind == 'fib') {
            $scope.label2Display = 'none';
            $scope.bDisplay = 'none';
            $scope.calc.b = '1';
        }
        $scope.keyUp();
        $scope.calc.operation = kind;
    };
    $scope.load = function () {
        if (!$scope.confirmFibonacci())
            return;
        $http({
            method: 'GET',
            url: "/rest/calc?a=" + $scope.calc.a + "&b=" + $scope.calc.b + "&operation=" + $scope.calc.operation
        }).then(function success(response) {
            $scope.message = response.data;
        }, function error(response) {
            alert("Недостаточно прав")
        });
    };

    $scope.keyUp = function () {
        if (isEmpty($scope.calc.a) || isEmpty($scope.calc.b) || ($scope.calc.operation == 'fib' && isEmpty($scope.calc.a)))
            $scope.buttonDisplay = false;
        else
            $scope.buttonDisplay = true;
        $scope.$apply();
    };

    $scope.visibleInterface = function () {
        $scope.aDisplay = 'block';
        $scope.bDisplay = 'block';
        $scope.calc.a = '';
        $scope.calc.b = '';
        $scope.label2Display = 'block';
        $scope.buttonDisplay = true;
        $scope.label1Display = 'block';
    };

    $scope.invisibleInterface = function () {
        $scope.operationName = '';
        $scope.aDisplay = 'none';
        $scope.bDisplay = 'none';
        $scope.label2Display = 'none';
        $scope.buttonDisplay = false;
        $scope.label1Display = 'none';
    };


    $scope.confirmFibonacci = function () {
        if ($scope.calc.operation == 'fib' && + $scope.calc.a > 50000) {
            return confirm('Вы ввели слишком большое число фибоначчи, ' +
                'его рассчёт может занять продолжительное время. Хотите продолжить?');
        }
        return true;
    };

    addEventListener("keyup", $scope.keyUp);
});

function isEmpty(str) {
    if (str === '' || str==null)
        return true;
    return false;
}