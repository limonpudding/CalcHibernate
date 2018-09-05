<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="container">
    <h3>Регистрация нового пользователя</h3>
    <h4>${error}</h4>
    <hr class="my-4">
    <form action="reg" method="post">
        <div class="form-group">
            <div class="form-group row">
                <input type="text" class="form-control" name="username" id="inputLogin" required autofocus placeholder="Логин">
            </div>
            <div class="form-group row">
                <input type="password" class="form-control" name="password" id="inputPassword" required placeholder="Пароль">
                <small id="passwordHelpInline" class="text-muted">
                    Must be 8-20 characters long.
                </small>
            </div>
            <div class="form-group row">
                <input type="password" class="form-control" name="rpassword" id="repeatPassword" required placeholder="Повторите пароль">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>
</div>