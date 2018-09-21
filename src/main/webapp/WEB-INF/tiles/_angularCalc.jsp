<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container" ng-controller="answerController" id="container">
    <script><%@include file="angularCalc.js"%></script>
    <form>
        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Выберите операцию
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#" onclick="summation()">Сложение!</a>
                <a class="dropdown-item" href="#" onclick="subtraction()">Вычитание!</a>
                <a class="dropdown-item" href="#" onclick="multiplication()">Умножение!</a>
                <a class="dropdown-item" href="#" onclick="division()">Деление!</a>
                <a class="dropdown-item" href="#" onclick="fibonacci()">Фибоначчи!</a>
                <a class="dropdown-item" href="#" onclick="invisibleInterface()">Очистка!</a>
            </div>
        </div>
        <label for="dropdownMenuButton" id="labelOperation"></label>
        <br>
        <label for="a" id="label1" style="display: none">Enter 1-st operator</label>
        <input type="number" id="a" name="a" style="display: none"><br>

        <label for="b" id="label2" style="display: none">Enter 2-nd operator</label>
        <input type="number" id="b" name="b" style="display: none"><br>

        <input type="text" id="operation" name="operation" style="display: none"><br>

        <input type="button" id="calcButton" value="Calculate" style="display: none" ng-click="load()"><br>


        <div class="form-group">
            <label for="exampleFormControlTextarea1">Ответ:</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="8" readonly>{{message}}</textarea>
        </div>
    </form>
</div>