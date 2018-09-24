//TODO вынести js скрипты в отдельные файлы
//TODO заменить ajax на http

var answerApp = angular.module("answerApp", []);
answerApp.controller("answerController", function ($scope, $http) {
    $scope.load = function () {
        if(!confirmFibonacci())
            return;
        $http({
            method: 'GET',
            url: "/rest/calc?a=" + $("#a").val() + "&b=" + $("#b").val() + "&operation=" + $("#operation").val()
        }).then(function success(response) {
            $scope.message = response.data;
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

function summation() {
    $("#labelOperation").html('Суммирование');
    visibleInterface();
    $("#operation").val('sum');
    $("#calcButton").css('display', 'none');
}

function subtraction() {
    $("#labelOperation").html('Вычитание')
    visibleInterface();
    $("#operation").val('sub');
    $("#calcButton").css('display', 'none');
}

function multiplication() {
    $("#labelOperation").html('Умножение')
    visibleInterface();
    $("#operation").val('mul');
    $("#calcButton").css('display', 'none');
}

function division() {
    $("#labelOperation").html('Деление');
    visibleInterface();
    $("#operation").val('div');
    $("#calcButton").css('display', 'none');
}

function fibonacci() {
    $("#labelOperation").html('Фибоначчи')
    visibleInterface();
    $("#label2").css('display', 'none');
    $("#b").css('display', 'none');
    $("#b").val('1');
    $("#operation").val('fib');
    $("#calcButton").css('display', 'none');
}

function confirmFibonacci() {
    if ($("#operation").val() === 'fib' && +$("#a").val() > 50000) {
        return confirm('Вы ввели слишком большое число фибоначчи, ' +
            'его рассчёт может занять продолжительное время. Хотите продолжить?');
    }
    return true;
}

addEventListener("keyup", keyUp);