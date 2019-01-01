import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

public class Subject {

    public static void main(String[] args) {
        asyncSubject();
    }

    private static void asyncSubject() {

        AsyncSubject asyncSubject = AsyncSubject.create();

        asyncSubject.subscribe(emtr -> System.out.println("Replay1: " + emtr));

        asyncSubject.onNext("One");
        asyncSubject.onNext("Two");
        asyncSubject.onNext("Three");
        asyncSubject.onComplete();

        asyncSubject.subscribe(emtr -> System.out.println("Replay2: " + emtr));
    }

    private static void replaySubject() {

        ReplaySubject replaySubject = ReplaySubject.create();

        replaySubject.subscribe(emtr -> System.out.println("Replay1: " + emtr));

        replaySubject.onNext("One");
        replaySubject.onNext("Two");
        replaySubject.onNext("Three");

        replaySubject.subscribe(emtr -> System.out.println("Replay2: " + emtr));
    }


    private static void behaviorSubject() {

        BehaviorSubject subject = BehaviorSubject.create();

        subject.subscribe(emtr -> System.out.println("Obs1: "+emtr));

        subject.onNext("One");
        subject.onNext("Two");
        subject.onNext("Three");

        subject.subscribe(emtr -> System.out.println("Obs2: "+emtr));

    }

    private static void publishSubjectWithTwoObservable(){

        Observable seconds = Observable.
                interval(1, TimeUnit.SECONDS).
                map(emtr -> emtr + 1 + " Seconds");

        Observable millisec = Observable.
                interval(500, TimeUnit.MILLISECONDS).
                map(emtr -> emtr + 1 + " Milli");

        PublishSubject subject = PublishSubject.create();
        subject.subscribe(System.out::println);

        seconds.subscribe(subject);
        millisec.subscribe(subject);

        Sleep(5);
    }

    private static void subject() {

        PublishSubject <String> subject = PublishSubject.create();

        subject.map(emtr -> emtr.length()).subscribe(System.out::println);

        subject.onNext("One");
        subject.onNext("Two");
        subject.onNext("Three");

    }

    public static void Sleep(long seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
