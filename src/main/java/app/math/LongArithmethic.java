package app.math;


import java.io.IOException;

public interface LongArithmethic extends Comparable<LongArithmethic> {

    void setValue(String strValue) throws IOException;

    void setLength(int length);

    byte getDigit(int index);

    void setDigit(byte digit, int index);

    int getLength();

    int getLengthMul();

    Sign getSign();

    void setSign(Sign sign);

    String toString();
}
