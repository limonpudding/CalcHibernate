package app.database.entities;

public enum Oper {
    SUM {
        public String toString() {
            return "SUM";
        }
    }, DIV {
        public String toString() {
            return "DIV";
        }
    }, SUB {
        public String toString() {
            return "SUB";
        }
    }, MUL {
        public String toString() {
            return "MUL";
        }
    }, FIB {
        public String toString() {
            return "FIB";
        }
    }
}
