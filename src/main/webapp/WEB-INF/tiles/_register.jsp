<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container">
    <h3>Регистрация нового пользователя</h3>
    <hr class="my-4">
    <form action="/reg" method="get">
        <div class="form-group">
            <div class="form-group row">
                <label for="inputLogin">Логин</label>
                <input type="text" class="form-control" name="username" id="inputLogin" placeholder="Login">
            </div>
            <div class="form-group row">
                <label for="inputPassword">Пароль</label>
                <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Пароль">
                <small id="passwordHelpInline" class="text-muted">
                    Must be 8-20 characters long.
                </small>
            </div>
            <div class="form-group row">
                <label for="repeatPassword">Повторите пароль</label>
                <input type="password" class="form-control" id="repeatPassword" placeholder="Повторите пароль">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>
</div>