import io.reactivex.Observable;
import io.reactivex.Single;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Operators {

    public static void filter(){

        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five");

        observable.filter(e -> e.length() > 3).
                subscribe(emtr -> System.out.println("Filtered " + emtr));
    }

    public static void take(){

        // take first three
        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five");

        observable.take(3).
                subscribe(emtr -> System.out.println("Take " + emtr));

        // take for first n seconds
        Observable interval = Observable.
                interval(500, TimeUnit.MILLISECONDS);

        interval.take(3, TimeUnit.SECONDS).
                subscribe(emtr -> System.out.println(emtr));

        Sleep(5);

        observable.takeLast(3).subscribe(emtr -> System.out.println(emtr));
    }


    public static void skip() {

        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five");

        observable.skip(3).
                subscribe(emtr -> System.out.println(emtr));

        Observable range = Observable.range(1,100);

        range.skip(95).
                subscribe(emtr -> System.out.println(emtr));
    }


    public static void whileObs() {

        Observable<Integer> range = Observable.range(1,10);
        range.skipWhile(emtr -> emtr > 5 ).
                subscribe(emtr -> System.out.println(emtr));
        range.takeWhile(emtr -> emtr > 5 ).
                subscribe(emtr -> System.out.println(emtr));
    }

    public static void distinct() {

        Observable<Integer> range = Observable.just(1,1,2,2,2,3,4,4,5,4);
        System.out.println("Distinct ");
        range.distinct().subscribe(emtr -> System.out.println(emtr));

        System.out.println("Distinct Until change");
        range.distinctUntilChanged().
                subscribe(emtr -> System.out.println(emtr));
    }

    public static void map() {

        Observable<String> datetime = Observable.
                just("1/1/2010","2/3/2015","3/4/2018");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.
                ofPattern("M/d/yyyy");

        datetime.map(s -> LocalDate.parse(s, dateTimeFormatter)).
                subscribe(emtr -> System.out.println(emtr));
    }

    public static void startWith() {

        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five");

        observable.startWith("Counting").
                subscribe(System.out::println);

        observable.startWithArray("Counting","--------").
                subscribe(System.out::println);
    }


    public static void ifEmpty() {

        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five");

        observable.filter(s-> s.length()> 10).
                defaultIfEmpty("Not Found").
                subscribe(System.out::println);

        observable.filter(s-> s.length()> 10).
                switchIfEmpty(Observable.just("Six","Seven","Eight")).
                subscribe(System.out::println);

    }


    public static void sorted() {

        Observable<Integer> range = Observable.just(1,2,3,5,4);

        range.sorted().subscribe(System.out::println);

        range.sorted(Comparator.reverseOrder()).
                subscribe(System.out::println);

        Observable<String> observable = Observable.
                just("One", "Two", "Thee", "Four", "Five","Six");

        observable.sorted(Comparator.comparingInt(String::length)).
                subscribe(System.out::println);
    }


    public static void delay() {

        Observable<Integer> range = Observable.just(1,2,3,5,4);

        range.delay(5 ,TimeUnit.SECONDS).
                subscribe(System.out::println);

        Sleep(10);
    }

    public static void repeat() {

        Observable<Integer> range = Observable.just(1,2,3,5,4);

        range.repeat(2).subscribe(System.out::println,Throwable::printStackTrace,() -> System.out.println("Completed"));

    }

    public static void scan() {

        Observable<Integer> range = Observable.just(1,2,3,4,5);

        range.scan((integer, integer2) -> integer + integer2).subscribe(System.out::println);

    }

    public static void count() {

        Observable<Integer> range = Observable.just(1,2,3,5,4);

        Single single = range.count();

        single.subscribe(System.out::println);

    }

    public static void reduce() {

        // Observable<Integer> range = Observable.just(1,2,3,5,4);

        Observable<String> range = Observable.
                just("1","2","3","4","5");

        range.reduce((integer, integer2) -> integer + " + "+ integer2).
                subscribe(System.out::println);

        //range.reduce(100, (integer, integer2) -> integer + integer2).subscribe(System.out::println) ;
    }


    public static void all() {

        Observable<Integer> range = Observable.just(1,2,3,5,4);

        int check = 10;
        Single single = range.all(emtr -> emtr < check);
        single.subscribe(answer -> System.out.println("All values are greater than " + check + ": " + answer));

        single = range.all(emtr -> emtr > check);
        single.subscribe(answer -> System.out.println("All values are less than " + check + ": " + answer));

        Observable <Integer> observable = Observable.empty();

        observable.all(emtr -> emtr > check).
                subscribe(emtr -> System.out.println("Vacuous Truth " + emtr ));

        observable.all(emtr -> emtr < check).
                subscribe(emtr -> System.out.println("Vacuous Truth " + emtr ));

    }

    public static void any(){

        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        int check = 3;
        observable.any(emtr -> emtr > check).
                subscribe(answer -> System.out.println(answer));

        observable = Observable.
                empty();

        observable.any(emtr -> emtr > check).
                subscribe(emtr -> System.out.println("Vacuous Truth " + emtr ));

        observable.any(emtr -> emtr < check).
                subscribe(emtr -> System.out.println("Vacuous Truth " + emtr ));
    }

    public static void contains(){

        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        int check = 3;
        observable.contains(check).subscribe(answer -> System.out.println(answer));
    }

    public static void toList(){

        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        observable.toList().subscribe(answer -> System.out.println(answer));
    }

    public static void toMap(){

        Observable <String> observable = Observable.just("One", "Two", "Thee", "Four", "Five","Six");

        observable.toMap(key -> key.charAt(0)).
                subscribe(answer -> System.out.println(answer));

        observable.toMap(key -> key.length()).
                subscribe(answer -> System.out.println(answer));

        observable.toMap(key -> key.charAt(0), key -> key.length()).
                subscribe(answer -> System.out.println(answer));
    }

    public static void onError(){

        Observable <Integer> observable = Observable.just(1,2,0,4,5);

        observable.map(emtr -> 10 / emtr).subscribe(integer -> System.out.println(integer),
                throwable -> System.out.println("Error :" +  throwable.toString()),
                () -> System.out.println("Completed"));

        observable.map(emtr -> 10 / emtr).onErrorReturnItem(-1).subscribe(integer -> System.out.println(integer),
                throwable -> System.out.println("Error :" +  throwable.toString()),
                () -> System.out.println("Completed"));

        observable.map(emtr -> 10 / emtr).onErrorReturn(throwable -> -1).subscribe(integer -> System.out.println(integer),
                throwable -> System.out.println("Error :" +  throwable.toString()),
                () -> System.out.println("Completed"));

        observable.map(emtr -> { try {return 10 / emtr;} catch(Exception e) { return -1;} } ).onErrorReturn(throwable -> -1).subscribe(integer -> System.out.println(integer),
                throwable -> System.out.println("Error :" +  throwable.toString()),
                () -> System.out.println("Completed"));

    }

    public static void doOn(){

        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        observable.doOnNext(integer -> System.out.println("What should i do on next with " + integer )).
                subscribe(answer -> System.out.println(answer));

        observable.doOnComplete(() -> System.out.println("I think i have completed")).
                subscribe(integer -> System.out.println(integer));

        observable.doAfterNext( integer -> System.out.println("I think i am after next")).
                subscribe(integer -> System.out.println(integer));

        observable = Observable.just(1,2,0,4,5);

        observable.doOnError(integer -> System.out.println("Before map")).
                map(i -> 10 / i).
                doOnError(integer -> System.out.println("After Map")).
                subscribe(integer -> System.out.println(integer), throwable -> System.out.println(throwable));
    }

    public static void onDispose(){

        Observable<Integer> observable = Observable.just(1,2,3,4,5);

        observable.
                doOnSubscribe(disposable -> System.out.println("subscribing")).
                doOnDispose(() -> System.out.println("Emission is disposed off")).
                subscribe(integer -> System.out.println(integer));

    }

    public static void main(String[] args) {
        onDispose();
    }


    public static void Sleep(long seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
