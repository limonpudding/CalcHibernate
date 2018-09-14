package app.pages.logic;

import app.database.entities.Operation;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedList;

class OperationsHistory {

    private LinkedList<Operation> operationsHistory = new LinkedList<>();

    LinkedList<Operation> getHistory(HttpSession session) {
        Object attribute = session.getServletContext().getAttribute(session.getId());
        if (!(attribute instanceof Collection)) {
            operationsHistory = new LinkedList<>();
        } else {
            operationsHistory = (LinkedList<Operation>) attribute;
        }
        return operationsHistory;
    }

    LinkedList<Operation> getHistory(){
        return operationsHistory;
    }

    void addOperation(Operation operation) {
        operationsHistory.addFirst(operation);
    }
}
