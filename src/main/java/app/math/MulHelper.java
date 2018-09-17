package app.math;

import java.io.IOException;

public class MulHelper implements Runnable {

    private LongArithmethic a;
    private int digit;
    private int i;
    private LongArithmethic result;

    public MulHelper(LongArithmethic a, int digit, int i, LongArithmethic result) {
        this.a = a;
        this.digit = digit;
        this.i = i;
        this.result = result;
    }

    @Override
    public void run() {
        int tmp1, tmp = 0;
        for (int j = 0; j < a.getLength(); ++j) {
            tmp1 = result.getDigit(j + i);
            result.setDigit((byte) ((a.getDigit(j) * digit + tmp1 + tmp) % 10), j + i);
            tmp = (byte) (tmp1 + a.getDigit(j) * digit + tmp) / 10;
        }
        if (tmp > 0) {
            result.setDigit((byte) (result.getDigit(result.getLengthMul()) + (tmp % 10)), result.getLengthMul());
        }
        result.setLength(result.getLength());
    }
}
