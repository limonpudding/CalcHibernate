package app.database.entities;

public enum OperationKind {
    SUM("SUM", "Сложение"),
    DIV("DIV", "Деление"),
    SUB("SUB", "Вычитание"),
    MUL("MUL", "Умножение"),
    FIB("FIB", "Фибоначчи");

    private String kind;
    private String fancyName;

    public String getKind() {
        return kind;
    }

    public String getFancyName() {
        return fancyName;
    }

    OperationKind(String kind, String fancyName) {
        this.kind = kind;
        this.fancyName = fancyName;
    }

    public static OperationKind getOperationKind(String oper) {
        for (OperationKind operation : values()) {
            if (operation.getKind().equals(oper.toUpperCase())) {
                return operation;
            }
        }
        return null;
    }
}
