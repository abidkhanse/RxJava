import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class Parallel {

    public static void main(String[] args) {
        fun();
    }

    private static void fun() {

        Observable <Integer> observable = Observable.range(1,10);

        observable.flatMap(
                emtr -> Observable.just(emtr).subscribeOn(Schedulers.computation()).
                map(e -> ExpensiveCalculation(e.toString()))).
                subscribe(a -> System.out.println(a + " " + LocalTime.now() + " " + Thread.currentThread().getName()));

        Sleep(10);

    }

    private static void simple() {

        Observable <Integer> observable = Observable.range(1,10);
        observable.map(emtr -> ExpensiveCalculation(emtr.toString())).
                subscribe(emtr -> System.out.println(emtr + " "+ LocalTime.now()));

    }


    public static String ExpensiveCalculation(String value){
        long sec = ThreadLocalRandom.current().nextInt(3000);
        Sleep(sec/1000);
        return value;
    }


    public static void Sleep(long seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
