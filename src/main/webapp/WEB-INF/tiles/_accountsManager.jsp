<%@ page import="app.database.entities.Roles" %>
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
<script>
    function changeUserRole(username) {
        var userRole = {
            'username': username,
            'role': $('#' + username).val()
        };
        $.ajax({
            method: "POST",
            url: "/roleChange",
            data: userRole,
            statusCode: {
                200: function () { // выполнить функцию если код ответа HTTP 200
                    console.log("Ok");
                }
            }
        });
    }
</script>

<div class="container" style="height: 80%;overflow-y: auto">

    <h4>Управление пользователями </h4>
    <c:forEach var="user" items="${users}">
        <form class="form-inline">
            <div class="form-group row">
                <div class="col">
                    <label for="${user.getUsername()}">${user.getUsername()}</label>
                </div>
                <div class="col">
                    <select class="form-control" id="${user.getUsername()}" onchange="changeUserRole('${user.getUsername()}')">
                        <%--<jsp:useBean id="Roles" scope="application" type="app.database.entities.Roles"/>--%>
                        <c:forEach var="role" items="${Roles.values()}">
                            <c:choose>
                                <c:when test="${user.getRole().toString()==role.toString()}">
                                    <option value="${role.toString()}" selected>${role.getName()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${role.toString()}">${role.getName()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>
    </c:forEach>
</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>



