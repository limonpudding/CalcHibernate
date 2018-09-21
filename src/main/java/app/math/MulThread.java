package app.math;

import app.utils.ApplicationContextProvider;

import java.util.concurrent.Callable;

public class MulThread implements Callable {
    private int threadsCount;
    private int it;
    private LongArithmethic b;
    private LongArithmethic a;

    MulThread(int threadsCount, int it, LongArithmethic b, LongArithmethic a) {
        this.threadsCount = threadsCount;
        this.it = it;
        this.b = b;
        this.a = a;
    }

    static LongArithmethic mulHelp(int digit, int i, LongArithmethic a) {
        int tmp1, tmp = 0;
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        for (int j = 0; j < a.getLength(); ++j) {
            tmp1 = result.getDigit(j + i);
            result.setDigit((byte) ((a.getDigit(j) * digit + tmp1 + tmp) % 10), j + i);
            tmp = (byte) (tmp1 + a.getDigit(j) * digit + tmp) / 10;
        }
        if (tmp > 0) {
            result.setDigit((byte) (result.getDigit(result.getLengthMul()) + (tmp % 10)), result.getLengthMul());
        }
        result.setLength(result.getLength());
        return result;
    }

    @Override
    public Object call() {
            LongArithmethic tempAnswer = LongConst.ZERO.getValue();
            for (int j = it; j < it + b.getLength() / threadsCount; ++j)
                tempAnswer = LongArithmeticMath.sum(mulHelp(b.getDigit(j), j, a), tempAnswer);
            return tempAnswer;
    }
}
