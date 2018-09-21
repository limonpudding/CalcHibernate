package app.math;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static app.math.MulThread.mulHelp;

public class MulThreadsLoader {
    private ExecutorService threadPool;
    private LongArithmethic a;
    private LongArithmethic b;
    private int threadsCount;

    public MulThreadsLoader(LongArithmethic a, LongArithmethic b, int threadsCount) {
        this.threadsCount = threadsCount;
        this.a = a;
        this.b = b;
    }

    public List<LongArithmethic> execute() {
        LinkedList<Future<LongArithmethic>> futures = new LinkedList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(threadsCount);
        LinkedList<LongArithmethic> rows = new LinkedList<>();
        for (int i = 0; i < b.getLength(); ++i) {
            futures.add(threadPool.submit(new MulThread(threadsCount, i, b, a)));
        }
        for (Future future : futures) {
            try {
                rows.add((LongArithmethic)future.get());
            } catch (Exception ignored) {
            }
        }

        for(int i=b.getLength()-b.getLength()%threadsCount;i<b.getLength();++i) {
            rows.add(mulHelp(b.getDigit(i), i, a));
        }
        return rows;
    }
}
