package app.database.entities;

public enum OperationKind {
    SUM ("SUM", "SUM"),
    DIV ("DIV", "DIV"),
    SUB ("SUB", "SUB"),
    MUL ("MUL", "MUL"),
    FIB ("FIB", "FIB");

    private String kind;
    private String tableName;

    public String getKind() {
        return kind;
    }

    public String getTableName() {
        return tableName;
    }

    OperationKind(String kind, String tableName) {
        this.kind = kind;
        this.tableName = tableName;
    }

    public static OperationKind getOperationKind(String oper) {
        oper=oper.toUpperCase();
        switch(oper) {
            case "SUM":
                return SUM;
            case "DIV":
                return DIV;
            case "SUB":
                return SUB;
            case "MUL":
                return MUL;
            case "FIB":
                return FIB;
            default:
                return null;
        }
    }
}
