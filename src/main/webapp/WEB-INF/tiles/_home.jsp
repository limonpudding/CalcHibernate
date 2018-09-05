<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="max-width: 540px; margin: auto; text-align: center; border-radius: 5px; padding: 20px">
    <h3>Добро пожаловать на главную страницу нашего приложения! Наша разработка - это калькулятор который способен выполнять операции над числами, количество знаков в которых может превышать 10000. Перейдите по ссылке <a href="calc">calculator</a>, чтоба начать им пользоваться!</h3>
    <h4>${message}</h4>
    <c:choose>
        <c:when test="${isFirstTime=='true'}">
            <h3>Добро пожаловать, новый посетитель!</h3>
        </c:when>
    </c:choose>
</div>
