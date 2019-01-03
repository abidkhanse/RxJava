import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Concurrency {

    public static void main(String[] args) {
        keepApplicationAliveForever();
    }

    /*
    Keep application alive for around 300 million years.
    * */
    private static void keepApplicationAliveForever() {

        Observable observable = Observable.interval(1, TimeUnit.SECONDS);

        observable.map(emtr -> emtr + "Seconds").subscribe(System.out::println);

        Sleep(Long.MAX_VALUE/1000);

    }

    private static void concurrentWithOperator() {

        Observable <String> observable = Observable.just("One","Two","Three","Four","Five");
        observable.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr));

        Observable <Integer> interval = Observable.range(1,6);
        interval.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr.toString()));

        Observable.zip(interval,observable, (integer,s) -> (integer + " - " + s)).subscribe(System.out::println);

        Sleep(10);
    }

    private static void concurrent() {

        Observable <String> observable = Observable.just("One","Two","Three","Four","Five");

        observable.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr)).
                subscribe(emtr -> System.out.println("Obs 1; "+emtr));

        Observable <Integer> interval = Observable.range(1,6);

        interval.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr.toString())).
                subscribe(emtr -> System.out.println("Obs 2; "+emtr));

        Sleep(10);
    }

    private static void notConcurrent() {

        Observable <String> observable = Observable.just("One","Two","Three","Four","Five");

        observable.map(emtr -> ExpensiveCalculation(emtr)).
                subscribe(emtr -> System.out.println("Obs 1; "+emtr));

        Observable <Integer> interval = Observable.range(1,6);

        interval.map(emtr -> ExpensiveCalculation(emtr.toString())).
                subscribe(emtr -> System.out.println("Obs 2; "+emtr));

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
