<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="operationsHistory" scope="request" type="java.util.List"/>
<c:forEach var="row" items="${operationsHistory}">
    <tr>
        <td class="col hidden" title="${row.getOperationKind().getFancyName()}">
                ${row.getOperationKind().getFancyName()}
        </td>
        <td class="col hidden" title="${row.getFirstOperand()}">
                ${row.getFirstOperand()}
        </td>
        <td class="col hidden" title="${row.getSecondOperand()}">
                ${row.getSecondOperand()}
        </td>
        <td class="col hidden" title="${row.getAnswer()}">
                ${row.getAnswer()}
        </td>
        <td class="col hidden" title="${row.getTime()}">
                ${row.getTime()}
        </td>
    </tr>
</c:forEach>