import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Concurrency {

    public static void main(String[] args) {

        fun();
    }


    private static void fun() {

        Observable<String> observable = Observable.just("One", "Two", "Three", "Four", "Five");

        observable.subscribeOn(Schedulers.computation()).
                map(Concurrency::ExpensiveCalculation).
                subscribe( emtr -> System.out.println(emtr + " " + Thread.currentThread().getName()) );

        observable.subscribeOn(Schedulers.computation()).
                map(Concurrency::ExpensiveCalculation).
                subscribe( emtr -> System.out.println(emtr + " " + Thread.currentThread().getName()) );

        Sleep(10);


    }


    private static void executorService() {

        Observable <String> observable = Observable.just("One", "Two", "Three", "Four", "Five");

        final int count = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(count);
        Scheduler subscriber = Schedulers.from(executorService);

        observable.subscribeOn(subscriber).
                doFinally(executorService::shutdown).
                map(Concurrency::ExpensiveCalculation).
                subscribe(System.out::println);

    }

    private static void single() {

        Observable <String> observable = Observable.just("One", "Two", "Three", "Four", "Five");

        observable.subscribeOn(Schedulers.single()).
                map(emtr -> ExpensiveCalculation(emtr)).
                blockingSubscribe(System.out::println);
    }


    private static void newThread() {

        Observable <String> observable = Observable.just("One", "Two", "Three", "Four", "Five");

        observable.subscribeOn(Schedulers.newThread()).
                map(emtr -> ExpensiveCalculation(emtr)).
                blockingSubscribe(System.out::println);
    }



    private static void schedulersComputation() {

        Observable <String> observable = Observable.just("One", "Two", "Three", "Four", "Five");

        observable.subscribeOn(Schedulers.computation()).
                map(Concurrency::ExpensiveCalculation).
                blockingSubscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Done"));
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

        // String Observable
        Observable <String> observable = Observable.just("One","Two","Three","Four","Five");

        observable.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr)).
                subscribe(emtr -> System.out.println("Obs 1; "+emtr));

        // String Integer
        Observable <Integer> interval = Observable.range(1,6);

        interval.subscribeOn(Schedulers.computation()).
                map(emtr -> ExpensiveCalculation(emtr.toString())).
                subscribe(emtr -> System.out.println("Obs 2; "+emtr));

        Sleep(10);
    }


    /*
    Keep application alive for around 300 million years.
    * */
    private static void keepApplicationAliveForever() {

        Observable observable = Observable.interval(1, TimeUnit.SECONDS);

        observable.map(emtr -> emtr + "Seconds").subscribe(System.out::println);

        Sleep(Long.MAX_VALUE/1000);

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
