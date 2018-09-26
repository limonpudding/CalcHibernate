<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container" ng-controller="answerController" id="container">
    <script charset="utf-8"><%@include file="angularCalc.js"%></script>
    <form>
        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Выберите операцию
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#" ng-click="calculate('sum', 'Сложение')">Сложение!</a>
                <a class="dropdown-item" href="#" ng-click="calculate('sub', 'Вычитание')">Вычитание!</a>
                <a class="dropdown-item" href="#" ng-click="calculate('mul', 'Умножение')">Умножение!</a>
                <a class="dropdown-item" href="#" ng-click="calculate('div', 'Деление')">Деление!</a>
                <a class="dropdown-item" href="#" ng-click="calculate('fib', 'Фибоначчи')">Фибоначчи!</a>
            </div>
        </div>
        <label for="dropdownMenuButton" id="labelOperation">{{operationName}}</label>
        <br>
        <label for="a" id="label1" style="display: {{label1Display}}">Enter 1-st operator</label>
        <input type="number" id="a" name="a" style="display: {{aDisplay}}" ng-model="calc.a" value={{calc.a}}><br>

        <label for="b" id="label2" style="display: {{label2Display}}">Enter 2-nd operator</label>
        <input type="number" id="b" name="b" style="display: {{bDisplay}}" ng-model="calc.b" value={{calc.b}}><br>

        <input type="button" id="calcButton" value="Calculate" style="display: {{buttonDisplay}}" ng-click="load()"><br>


        <div class="form-group">
            <label for="exampleFormControlTextarea1">Ответ:</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="8" readonly>{{message}}</textarea>
        </div>
    </form>
</div>
