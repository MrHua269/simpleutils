import org.prawa.simpleutils.concurrent.forkjointasks.ParallelListTraverse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class test {
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < 65535; i++) {
            ints.add(i);
        }
        long timeNow = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ints.forEach(count::getAndAdd);
        System.out.println("Single thread time :" + (System.currentTimeMillis() - timeNow));
        timeNow = System.currentTimeMillis();
        ints.parallelStream().forEach(count::getAndAdd);
        System.out.println("Parallel stream time :" + (System.currentTimeMillis() - timeNow));
        timeNow = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        ParallelListTraverse<Integer> traverse = new ParallelListTraverse<>( ints, 4,count::getAndAdd);
        pool.submit(traverse).join();
        System.out.println("ForkJoin Parallel time :" + (System.currentTimeMillis() - timeNow));
        System.out.println("Count :" + count.get());
    }
}
