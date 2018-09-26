var answerApp = angular.module("answerApp", []);
answerApp.controller("answerController", function ($scope, $http) {
    $scope.calc = {
        a: '',
        b: '',
        operation: 'sum'
    };
    $scope.summation = function () {
        $("#labelOperation").html('Суммирование');
        visibleInterface();
        $("#calcButton").css('display', 'none');
        $scope.calc.operation="sum";
    }

    $scope.subtraction = function () {
        $("#labelOperation").html('Вычитание')
        visibleInterface();
        $("#calcButton").css('display', 'none');
        $scope.calc.operation="sub";
    }

    $scope.multiplication = function () {
        $("#labelOperation").html('Умножение')
        visibleInterface();
        $("#calcButton").css('display', 'none');
        $scope.calc.operation="mul";
    }

    $scope.division = function () {
        $("#labelOperation").html('Деление');
        visibleInterface();
        $("#calcButton").css('display', 'none');
        $scope.calc.operation="div";
    }

    $scope.fibonacci = function () {
        $("#labelOperation").html('Фибоначчи')
        visibleInterface();
        $("#label2").css('display', 'none');
        $("#b").css('display', 'none');
        $("#b").val('1');
        $("#calcButton").css('display', 'none');
        $scope.calc.operation="fib";
    }
    $scope.load = function () {
        if(!confirmFibonacci())
            return;
        $http({
            method: 'GET',
            url: "/rest/calc?a=" + $scope.calc.a + "&b=" + $scope.calc.b + "&operation=" + $scope.calc.operation
        }).then(function success(response) {
            $scope.message = response.data;
        }, function error(response) {
            alert("Недостаточно прав")
        });
    }
});

function isEmpty(str) {
    if (str.trim() == '')
        return true;
    return false;
}

function keyUp(e) {
    if (isEmpty($("#a").val()) || isEmpty($("#b").val()) || ($("#operation").val() === 'fib' && isEmpty($("#a").val())))
        $("#calcButton").css('display', 'none');
    else
        $("#calcButton").css('display', 'block');
}

function visibleInterface() {
    $("#a").css('display', 'block');
    $("#b").css('display', 'block');
    $("#a").val('');
    $("#b").val('');
    $("#label2").css('display', 'block');
    $("#calcButton").css('display', 'block');
    $("#label1").css('display', 'block');
}

function invisibleInterface() {
    $("#labelOperation").html('');
    $("#a").css('display', 'none');
    $("#b").css('display', 'none');
    $("#label2").css('display', 'none');
    $("#calcButton").css('display', 'none');
    $("#label1").css('display', 'none');
}



function confirmFibonacci() {
    if ($("#operation").val() === 'fib' && +$("#a").val() > 50000) {
        return confirm('Вы ввели слишком большое число фибоначчи, ' +
            'его рассчёт может занять продолжительное время. Хотите продолжить?');
    }
    return true;
}

addEventListener("keyup", keyUp);