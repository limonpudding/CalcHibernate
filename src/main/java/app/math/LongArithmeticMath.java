package app.math;

import app.utils.ApplicationContextProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.*;

@Component
public class LongArithmeticMath {

    @Autowired
    static BeanFactory beanFactory;

    private static int n = 10000;//максимальная длина числа

    public static void setDigitsCount(int dimension) {
        n = dimension;
    }

    public static LongArithmethic sum(LongArithmethic addendum, LongArithmethic term) {
        LongArithmethic a = addendum;
        LongArithmethic b = term;
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        result.setSign(a.getSign());
        if (a.getSign() == Sign.PLUS && b.getSign() == Sign.MINUS) {
            b.setSign(Sign.PLUS);
            return sub(a, b);
        }
        if (a.getSign() == Sign.MINUS && b.getSign() == Sign.PLUS) {
            a.setSign(Sign.PLUS);
            return sub(b, a);
        }
        int maxLength = a.getLength() > b.getLength() ? a.getLength() : b.getLength();
        int tmp = 0;
        int i;
        for (i = 0; i < maxLength; ++i) {
            result.setDigit((byte) ((a.getDigit(i) + b.getDigit(i) + tmp) % 10), i);
            tmp = (a.getDigit(i) + b.getDigit(i) + tmp) / 10;
        }
        result.setDigit((byte) tmp, i);
        result.getLength();
        return result;
    }


    public static LongArithmethic mul(LongArithmethic multiplied, LongArithmethic factor) {
        LongArithmethic a = multiplied;
        LongArithmethic b = factor;
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        if (a.getSign() != b.getSign()) {
            result.setSign(Sign.MINUS);
        }
        int tmp = 0;
        int tmp1;
        int i;
        int j;
        for (i = 0; i < b.getLength(); ++i) {
            for (j = 0; j < a.getLength(); ++j) {
                tmp1 = result.getDigit(j + i);
                result.setDigit((byte) ((a.getDigit(j) * b.getDigit(i) + tmp1 + tmp) % 10), j + i);
                tmp = (byte) (tmp1 + a.getDigit(j) * b.getDigit(i) + tmp) / 10;
            }
            if (tmp > 0)

                result.setDigit((byte) (result.getDigit(result.getLengthMul()) + (tmp % 10)), result.getLengthMul());
            result.setLength(result.getLength());
            tmp = 0;
        }
        return result;
    }

    /**
     * Функция умножения двух длинных чисел
     *
     * @param multiplied первый множитель
     * @param factor     второй множитель
     * @return результат умножения
     */
    public static LongArithmethic newMul(LongArithmethic multiplied, LongArithmethic factor) {
        LongArithmethic a = multiplied;
        LongArithmethic b = factor;
        Stack<LongArithmethic> rows = new Stack<>();
        Stack<Thread> mulHelper = new Stack<>();
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        if (a.getSign() != b.getSign()) {
            result.setSign(Sign.MINUS);
        }
        for (int i = 0; i < b.getLength(); ++i) {
            LongArithmethic row = new LongArithmeticImplList();
            rows.add(row);
            mulHelper.add(new Thread(new MulHelper(a, b.getDigit(i), i, row)));
        }
        for (Thread thread : mulHelper) {
            thread.start();
        }
        for (Thread thread : mulHelper) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        return masSum(rows);
    }

    private static LongArithmethic mulHelp(int digit, int i, LongArithmethic a) {
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


    public static LongArithmethic futureMul(LongArithmethic multiplied, LongArithmethic factor) {
        LongArithmethic a = multiplied;
        LongArithmethic b = factor;
        Stack<Future<LongArithmethic>> futures = new Stack<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        try {
            result.setValue("0");
        } catch (IOException ignored) {
        }

        if (a.getSign() != b.getSign()) {
            result.setSign(Sign.MINUS);
        }
        for (int i = 0; i < b.getLength(); ++i) {
            final int it = i;
            futures.add(CompletableFuture.supplyAsync(
                    () -> {
                        System.out.println("Thread " + Thread.currentThread().getName() + " with params it = " + it + " start");
                        LongArithmethic partialResult = mulHelp(b.getDigit(it), it, a);
                        System.out.println("Thread " + Thread.currentThread().getName() + " with params it = " + it + " finish");
                        return partialResult;
                    },
                    threadPool
            ));
        }

        for (Future<LongArithmethic> future : futures) {
            try {
                result = LongArithmeticMath.sum(result, future.get());
            } catch (Exception ignored) {
            }
        }

        return result;
    }

    public static LongArithmethic futureMulAdv(LongArithmethic multiplied, LongArithmethic factor) {
        LongArithmethic a = multiplied;
        LongArithmethic b = factor;
        Stack<Future<LongArithmethic>> futures = new Stack<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        try {
            result.setValue("0");
        } catch (IOException ignored) {
        }

        if (a.getSign() != b.getSign()) {
            result.setSign(Sign.MINUS);
        }
        for (int i = 0; i < 8; ++i) {
            final int it = b.getLength()/8*i;
            futures.add(CompletableFuture.supplyAsync(
                    () -> {
                        LongArithmethic tempAnswer = LongConst.ZERO.getValue();
                        //System.out.println("Thread " + Thread.currentThread().getName() + " with params it = " + it + " start");
                        for (int j = it; j < it + b.getLength() / 8; ++j)
                            tempAnswer = LongArithmeticMath.sum(mulHelp(b.getDigit(it), it, a), tempAnswer);
                        //System.out.println("Thread " + Thread.currentThread().getName() + " with params it = " + it + " finish");
                        return tempAnswer;
                    },
                    threadPool
            ));
        }

        for (Future<LongArithmethic> future : futures) {
            try {
                result = LongArithmeticMath.sum(result, future.get());
            } catch (Exception ignored) {
            }
        }

        for(int i=b.getLength()-b.getLength()%8;i<b.getLength();++i) {
            result = LongArithmeticMath.sum(mulHelp(b.getDigit(i), i, a), result);
        }

        return result;
    }

    public static LongArithmethic masSum(Stack<LongArithmethic> terms) {
        LongArithmethic result = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);
        try {
            result.setValue("0");
        } catch (IOException ignored) {
        }
        for (LongArithmethic term : terms) {
            result = LongArithmeticMath.sum(term, result);
        }
        return result;
    }

    /**
     * Метод. применяемый к экземпляру объекта длинного числа.
     *
     * @return Возвращает строку (число со знаком или без)
     */


    /**
     * Функция вычитания двух длинных чисел
     *
     * @param minuend    Уменьшаемое значение
     * @param subtrahend Вычетаемое значение
     * @return Разность
     */
    public static LongArithmethic sub(LongArithmethic minuend, LongArithmethic subtrahend) {
        LongArithmethic a = minuend;
        LongArithmethic b = subtrahend;
        if (a.getSign() == Sign.PLUS && b.getSign() == Sign.MINUS) {
            b.setSign(Sign.PLUS);
            return sum(a, b);
        }
        if (a.getSign() == Sign.MINUS && b.getSign() == Sign.PLUS) {
            b.setSign(Sign.MINUS);
            return sum(b, a);
        }
        if (a.getSign() == Sign.MINUS && b.getSign() == Sign.MINUS) {
            b.setSign(Sign.PLUS);
            LongArithmethic temp = a;
            a = b;
            b = temp;
        }

        int maxLength = a.getLength() > b.getLength() ? a.getLength() : b.getLength();
        LongArithmethic c = ApplicationContextProvider.getApplicationContext().getBean(LongArithmethic.class);

        if (a.compareTo(b) == -1) {
            LongArithmethic temp = a;
            a = b;
            b = temp;
            c.setSign(Sign.MINUS);
        }

        for (int i = 0; i < maxLength; ++i) {
            if (a.getDigit(i) >= b.getDigit(i)) {
                c.setDigit((byte) (a.getDigit(i) - b.getDigit(i)), i);
            } else {
                c.setDigit((byte) (a.getDigit(i) - b.getDigit(i) + 10), i);
                a.setDigit((byte) (a.getDigit(i + 1) - 1), i + 1);
            }
        }
        c.getLength();
        return c;
    }

    /**
     * Функция деления без остатка двух длинных чисел
     *
     * @param a Делимое
     * @param b Делитель
     * @return Результат деления без остатка
     */
    public static LongArithmethic div(LongArithmethic a, LongArithmethic b) {
        LongArithmethic result;
        Sign sign = Sign.PLUS;
        if (b.toString().equals("0")) {
            throw new ArithmeticException("Division by zero!");
        }
        if (a.getSign() != b.getSign()) {
            sign = Sign.MINUS;
        }
        if (a.compareTo(b) < 0) {
            return LongConst.ZERO.getValue();
        } else if (a.compareTo(b) == 0) {
            result = LongConst.ONE.getValue();
            result.setSign(sign);
            return result;
        }
        result = LongConst.ONE.getValue();
        LongArithmethic tmp = b;
        Stack<LongArithmethic> temps = new Stack<LongArithmethic>();
        Stack<LongArithmethic> resultList = new Stack<LongArithmethic>();
        temps.push(tmp);
        resultList.push(result);
        while (a.compareTo(tmp) >= 0) {
            tmp = LongArithmeticMath.mul(tmp, LongConst.TWO.getValue());
            result = LongArithmeticMath.mul(result, LongConst.TWO.getValue());
            temps.push(tmp);
            resultList.push(result);
        }
        resultList.pop();
        temps.pop();

        result = resultList.pop();
        tmp = temps.pop();
        while (a.compareTo(tmp) != 0) {
            if (temps.isEmpty())
                break;
            LongArithmethic accumResultTmp = LongArithmeticMath.sum(tmp, temps.pop());
            LongArithmethic accumResult = LongArithmeticMath.sum(result, resultList.pop());
            if (accumResultTmp.compareTo(a) > 0) {
                //пусто
            } else if (accumResultTmp.compareTo(a) < 0) {
                result = accumResult;
                tmp = accumResultTmp;
            } else {
                result = accumResult;
                tmp = accumResultTmp;
            }
        }
        result.getLength();
        result.setSign(sign);
        return result;
    }
}
