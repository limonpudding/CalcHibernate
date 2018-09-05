<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="border-radius: 0 0 15px 15px; margin-bottom: 20px;">
    <div class="container">
    <a class="navbar-brand" href="/calc">Calculator</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1" aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse order-0" id="navbar1">
        <ul class="navbar-nav mr-auto">
            <li>
                <a class="nav-link" href="/">Домашняя</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/about">О ресурсе</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/calc">Вычисления</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/ophistory">История операций</a>
            </li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="nav-item">
                <a class="nav-link" href="/accountsManager">Управление аккаунтами</a>
            </li>
            </sec:authorize>

        </ul>
    </div>
        <div class="collapse navbar-collapse order-2"  id="navbar2">
        <ul class="navbar-nav mr-auto">
            <sec:authorize access="!isAuthenticated()">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="loginDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Авторизация</a>
                    <div class="dropdown-menu" aria-labelledby="loginDropdown">
                        <a class="dropdown-item" href="/login">Вход</a>
                        <a class="dropdown-item" href="/register">Регистрация</a>
                            <%--<div class="dropdown-divider"></div>--%>
                            <%--<a class="dropdown-item" href="#">Something else here</a>--%>
                    </div>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="loggedinDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.username" /></a>
                    <div class="dropdown-menu" aria-labelledby="loggedinDropdown">
                        <a class="dropdown-item" href="<c:url value="/logout" />">Выйти</a>
                    </div>
                </li>

            </sec:authorize>
            <%--<li class="nav-item dropdown">--%>
                <%--<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Авторизация</a>--%>
                <%--<div class="dropdown-menu" aria-labelledby="navbarDropdown1">--%>
                    <%--<a class="dropdown-item" href="/login">Вход</a>--%>
                    <%--<a class="dropdown-item" href="/register">Регистрация</a>--%>
                    <%--&lt;%&ndash;<div class="dropdown-divider"></div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a class="dropdown-item" href="#">Something else here</a>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</li>--%>
        </ul>
        <!--<form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-info my-2 my-sm-0" type="submit">Search</button>
        </form>-->
    </div>
    </div>
</nav>