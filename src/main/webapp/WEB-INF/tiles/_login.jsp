<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container">
    <h3>Авторизация</h3>
    <hr class="my-4">
    <c:url value="perform_login" var="loginUrl" />
    <form action = "${loginUrl}" method="post">
        <div class="form-group">
            <div class="form-group row">
                <input type="text" class="form-control" name="login" id="inputLogin" required autofocus placeholder="Логин">
            </div>
            <div class="form-group row">
                <input type="password" class="form-control" name="password" id="inputPassword" required placeholder="Пароль">
                <small id="passwordHelpInline" class="text-muted">
                    Must be 8-20 characters long.
                </small>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</div>