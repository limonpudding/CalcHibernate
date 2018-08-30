<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="fullSessionsHistory" scope="request" type="java.util.List"/>
<c:forEach var="row" items="${fullSessionsHistory}">
    <tr>
        <td class="col hidden" title="${row.getId()}">
            <c:choose>
                <c:when test="${!row.isOperationsExist()}">
                    ${row.getId()}
                </c:when>
                <c:otherwise>
                    <a href="#" onclick="openSecondTable('${row.getId()}')">${row.getId()}</a>
                </c:otherwise>
            </c:choose>
        </td>
        <td class="col hidden" title="${row.getIp()}">
                ${row.getIp()}
        </td>
        <td class="col">
                ${row.getTimeStart()}
        </td>
        <td class="col">
                ${row.getTimeEnd()}
        </td>
    </tr>
</c:forEach>