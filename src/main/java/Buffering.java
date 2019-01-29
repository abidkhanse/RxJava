import io.reactivex.Observable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Buffering {

    public static void main(String[] args) {
        fun();
    }

    private static void fun() {

        Observable<String> observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .map(emtr -> (emtr + 1) * 100)
                .map(emtr -> (emtr + " Subscribe 1"))
                .take(10);

        Observable <String> observable2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(emtr -> (emtr + 1) * 500)
                .map(emtr -> (emtr + " Subscribe 2"))
                .take(2);

        Observable <String> observable3 = Observable.interval(2, TimeUnit.SECONDS)
                .map(emtr -> (emtr + 1) * 2000)
                .map(emtr -> (emtr + " Subscribe 3"))
                .take(10);

        Observable.concat(observable1,observable2,observable3).
                throttleWithTimeout(1, TimeUnit.SECONDS).
                subscribe(System.out::println);

        Sleep(10);


    }

    private static void throttleFirst() {

        Observable <String> obs1 = Observable.interval(100, TimeUnit.MILLISECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr + " Subscribe 1")  .take(10);

        Observable <String> obs2 = Observable.interval(500, TimeUnit.MILLISECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr + " Subscribe 2").take(5);

        Observable <String> obs3 = Observable.interval(2, TimeUnit.SECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr  + " Subscribe 3").take(2);

        Observable.
                concat(obs1,obs2,obs3).
                throttleFirst(1, TimeUnit.SECONDS).
                        subscribe(System.out::println);

        Sleep(10);

    }

    private static void throttleLast() {

        Observable <String> obs1 = Observable.interval(100, TimeUnit.MILLISECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr + " Subscribe 1").take(10);

        Observable <String> obs2 = Observable.interval(500, TimeUnit.MILLISECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr + " Subscribe 2").take(5);

        Observable <String> obs3 = Observable.interval(2, TimeUnit.SECONDS).
                map(emtr -> (emtr + 1)).
                map(emtr -> emtr  + " Subscribe 3").take(2);

        Observable.
                concat(obs1,obs2,obs3).
                throttleLast(1, TimeUnit.SECONDS).
                //throttleLast(1, TimeUnit.SECONDS).
                subscribe(System.out::println);

        Sleep(7);
    }


    private static void boundaryWindow() {

        Observable boundary = Observable.interval(1,TimeUnit.SECONDS);
        Observable <Observable<Long>> observable1s = Observable.interval(500, TimeUnit.MILLISECONDS).
                window(boundary);

        observable1s.
                flatMapSingle(obs -> obs.reduce("",( total, next ) -> total + (total.equals("") ? "" : "|") + next)).
                subscribe(System.out::println);

        Sleep(5);
    }

    private static void timeBasedWindow() {

        Observable observable = Observable.interval(300, TimeUnit.MILLISECONDS);

        Observable <Observable<Long>> observable1s = observable.window(1,TimeUnit.SECONDS);

        observable1s.
                flatMapSingle(obs -> obs.reduce("",( total, next ) -> total + (total.equals("") ? "" : "|") + next))
                .subscribe(System.out::println);

        Sleep(5);


    }

    private static void windowSkip() {

        Observable observable = Observable.range(1,50);
        Observable <Observable<Integer>> observable1s = observable.window(5,3);
        observable1s.
                flatMapSingle(
                        obs -> obs.
                        reduce("",( total, next ) -> total + (total.
                        equals("") ? "" : "|") + next)
                ).subscribe(System.out::println);

    }

    private static void window() {

        Observable observable = Observable.range(1,50);
        Observable <Observable<Integer>> observable1s = observable.window(8);

        observable1s.
                flatMapSingle(obs -> obs.reduce("",( total, next ) -> total + (total.equals("") ? "" : "|") + next))
                .subscribe(System.out::println);
    }

    private static void bufferBasedBuffer() {

        Observable observable = Observable.interval(1, TimeUnit.SECONDS);
        Observable interval = Observable.interval(300,TimeUnit.MILLISECONDS);

        interval.map(emtr -> emtr + " Interval").
                buffer(observable).
                subscribe(System.out::println);

        Sleep(5);
    }

    private static void timeBasedMaxBuffer() {

        Observable observable = Observable.interval(300, TimeUnit.MILLISECONDS);

        observable.map(emtr -> emtr + " Interval").
                buffer(1, TimeUnit.SECONDS,2).
                subscribe(System.out::println);

        Sleep(5);

    }

    private static void timeBasedBuffer() {

        Observable observable = Observable.interval(300, TimeUnit.MILLISECONDS);
        observable.map(emtr -> emtr + " Interval").
                buffer(1, TimeUnit.SECONDS).
                subscribe(System.out::println);

        Sleep(5);

    }



    private static void bufferSkip() {

        Observable observable = Observable.range(1,10);

        observable.buffer(2).
                subscribe(System.out::println);

        observable.buffer(2,4).
                subscribe(System.out::println);

        observable.buffer(4,2).
                subscribe(System.out::println);

    }

    private static void buffer() {
        Observable observable = Observable.range(1,50);
        observable.buffer(10).
                subscribe(System.out::println);
    }


    public static String ExpensiveCalculation(String value) {
        long sec = ThreadLocalRandom.current().nextInt(3000);
        Sleep(sec / 1000);
        return value;
    }


    public static void Sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}