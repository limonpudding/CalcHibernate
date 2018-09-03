<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container">
    <h3>Авторизация</h3>
    <hr class="my-4">
    <form action method="post">
        <div class="form-group">
            <div class="form-group row">
                <label for="inputLogin">Логин</label>
                <input type="email" class="form-control" id="inputLogin" placeholder="Login">
            </div>
            <div class="form-group row">
                <label for="inputPassword">Пароль</label>
                <input type="password" class="form-control" id="inputPassword" placeholder="Пароль">
                <small id="passwordHelpInline" class="text-muted">
                    Must be 8-20 characters long.
                </small>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</div>