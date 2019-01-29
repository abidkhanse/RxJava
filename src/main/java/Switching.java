import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Switching {

    public static void main(String []args){
        switchMap();
    }

    private static void switchMap() {

        Observable <String> observable = Observable.just(
                "One","Two","Three",
                "Four","Five","Six",
                "Seven","Eight","Nine","Ten"
        );

        Observable <String> processString = observable.concatMap(emtr ->
                Observable.just(emtr).
                        delay(randomSleepTime(),TimeUnit.MILLISECONDS));

        Observable.interval(5, TimeUnit.SECONDS).
                switchMap(aLong -> processString.doOnDispose(() -> System.out.println("Dispose in 5 seconds"))).
                subscribe(System.out::println);

        Sleep(20);
    }

    public static String ExpensiveCalculation(String value) {
        long sec = ThreadLocalRandom.current().nextInt(3000);
        Sleep(sec / 1000);
        return value;
    }


    public static long randomSleepTime(){
        return ThreadLocalRandom.current().nextInt(3000);
    }


    public static void Sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
