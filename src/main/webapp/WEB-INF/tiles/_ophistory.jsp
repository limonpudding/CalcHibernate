<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<style>
    .table-fixed thead {
        width: 97%;
    }

    .table-fixed tbody {
        height: 230px;
        overflow-y: auto;
        width: 100%;
    }

    .table-fixed thead, .table-fixed tbody, .table-fixed tr, .table-fixed td, .table-fixed th {
        display: block;
    }

    .table-fixed tbody td, .table-fixed thead > tr > th {
        float: left;
        border-bottom-width: 0;
    }

    .hidden {
        white-space: nowrap; /* Отменяем перенос текста */
        overflow: hidden; /* Обрезаем содержимое */
        text-overflow: ellipsis;
    }
</style>
<script><%@include file="ophistory.js"%></script>
<div class="container" style="height: 80%;overflow-y: auto" ng-controller="answerController">
    <div id="carouselExampleControls" class="carousel slide" data-ride="false" data-pause="true">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <h4>Сортировка: </h4>
                <form action="ophistory" method="get">
                    <div class="form-row">
                        <div class="form-group col-auto">
                            <select class="custom-select" name="mode" id="firstSelectorMode"
                                    ng-model="sessionsSort.mode">
                                <option value="id">ID</option>
                                <option value="ip">IP</option>
                                <option value="timeStart" selected>Время создания сессии</option>
                                <option value="timeEnd">Время завершения сессии</option>
                            </select>
                        </div>
                        <div class="form-group col-auto">
                            <select class="custom-select" name="order" id="firstSelectorDirection"
                                    ng-model="sessionsSort.direction">
                                <option value="asc">По возрастанию</option>
                                <option value="desc" selected>По убыванию</option>
                            </select>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="button" ng-click="loadFirst()"
                                   value="Выбрать">
                        </div>
                    </div>
                </form>
                <table class="table" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th class="col hidden">
                            Количество операций
                        </th>
                        <th class="col hidden">
                            ID
                        </th>
                        <th class="col">
                            IP
                        </th>
                        <th class="col" onclick="">
                            Время создания сессии
                        </th>
                        <th class="col">
                            Время завершения сессии
                        </th>
                    </tr>
                    </thead>
                    <tbody id="firstTable">
                    <tr ng-repeat="row in firstTable">
                        <td class="col hidden" title={{row.operationsCount}}>{{row.operationsCount}}</td>
                        <td class="col hidden" title={{row.id}} ng-if="row.operationsCount != 0"><a href="#"
                                                                                                    ng-click="openSecondTable(row.id)">{{row.id}}</a>
                        </td>
                        <td class="col hidden" title={{row.id}} ng-if="row.operationsCount == 0">{{row.id}}</td>
                        <td class="col hidden" title={{row.ip}}>{{row.ip}}</td>
                        <td class="col hidden" title={{row.timeStart}}>{{row.timeStart}}</td>
                        <td class="col hidden" title={{row.timeEnd}}>{{row.timeEnd}}</td>
                        <%--заполняется динамически--%>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="carousel-item">
                <h4>Сортировка: </h4>
                <form action="ophistory" method="get" id="sortForm">
                    <div class="form-row">
                        <div class="form-group col-auto">
                            <select class="custom-select" name="mode" id="secondSelectorMode"
                                    ng-model="operationsSort.mode">
                                <option value="operationKind">Операция</option>
                                <option value="time">Время операции</option>
                            </select>
                        </div>
                        <div class="form-group col-auto">
                            <select class="custom-select" name="order" id="secondSelectorDirection"
                                    ng-model="operationsSort.direction">
                                <option value="asc">По возрастанию</option>
                                <option value="desc">По убыванию</option>
                            </select>
                        </div>
                        <div class="col">
                            <input class="btn btn-primary" type="button" ng-click="loadSecond()"
                                   value="Выбрать">
                            <input class="btn btn-secondary" type="button" onclick="slidePrev()" value="Назад">
                        </div>
                    </div>
                </form>
                <table class="table" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th class="col">
                            Операция
                        </th>
                        <th class="col">
                            Первый операнд
                        </th>
                        <th class="col">
                            Второй операнд
                        </th>
                        <th class="col">
                            Ответ
                        </th>
                        <th class="col">
                            Время операции
                        </th>
                    </tr>
                    </thead>
                    <tbody id="secondTable">
                    <tr ng-repeat="row in secondTable">
                        <td class="col hidden" title={{row.operationKind}}>{{row.operationKind}}</td>
                        <td class="col hidden" title={{row.firstOperand}}>{{row.firstOperand}}</td>
                        <td class="col hidden" title={{row.secondOperand}}>{{row.secondOperand}}</td>
                        <td class="col hidden" title={{row.answer}}>{{row.answer}}</td>
                        <td class="col hidden" title={{row.time}}>{{row.time}}</td>
                    </tr>
                    <%--заполняется динамически--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>


