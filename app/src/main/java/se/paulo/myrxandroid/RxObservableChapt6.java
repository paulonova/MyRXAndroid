package se.paulo.myrxandroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

/** * Created by Paulo Vila Nova on 2017-05-29.
 */

public class RxObservableChapt6 {

    List<String > poemsPlayList = Arrays.asList("Poem 1", "Poem 2", "Poem 3");


    public void usingColdObservable(){

        Observable coldMusicCoffeCafe = Observable.fromArray(poemsPlayList);
        Consumer client1 = poem -> Log.i("POEM", "" + poem);
        Consumer client2 = poem -> Log.i("POEM", "" + poem);
        Consumer client3 = poem -> Log.i("POEM", "" + poem);
        Consumer client4 = poem -> Log.i("POEM", "" + poem);

        coldMusicCoffeCafe.subscribe(client1);
        coldMusicCoffeCafe.subscribe(client2);
        Log.i("POEM", "" + System.currentTimeMillis());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("POEM", "" + System.currentTimeMillis());
        coldMusicCoffeCafe.subscribe(client3);
        coldMusicCoffeCafe.subscribe(client4);
    }



    public void usingHotObservable(){

        Observable<Long> hotMusicCoffeeCafe = Observable.interval(1000, TimeUnit.MILLISECONDS);
        ConnectableObservable<Long> connectableObservable = hotMusicCoffeeCafe.publish();
        connectableObservable.connect(); //  Cafe open on this line and cafe boy start the system


        Consumer client1 = poem-> System.out.println("Client 1 poem "+poem);
        Consumer client2 = poem-> System.out.println("Client 2 poem "+poem);
        Consumer client3 = poem-> System.out.println("Client 3 poem "+poem);
        Consumer client4 = poem-> System.out.println("Client 4 poem "+poem);

        try {
            Thread.sleep(2000); // After two poems already played client 1 enter. So he should listens from poem 2.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectableObservable.subscribe(client1);

        try {
            Thread.sleep(1000); // Client two should start listening poem 3
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectableObservable.subscribe(client2);

        try {
            Thread.sleep(4000); // Client 3 and 4 enter will start from poem 9.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectableObservable.subscribe(client3);
        connectableObservable.subscribe(client4);



    }


    /**publish() will convert Cold Observable to Hot but never start data emission.
     * For data emission we need to call a connect() method*/
    public void convertColdInHotObservable(){

        List<Integer> integers = new ArrayList<>();
        Observable.range(0, 10)
                .subscribe(count -> integers.add(count));

        Observable<List<Integer>> listObservable = Observable.fromArray(integers);

        //Converting..
        ConnectableObservable connectableObservable = listObservable.publish();
        connectableObservable.subscribe(s -> Log.i("AQUI", ">>> " + s));
        connectableObservable.connect(); //start emitting


    }

    public void maybeColdObservable(){

        Random random = new Random();

        Observable<String> just = Observable.just("Hello guys");
        just.subscribe(s-> Log.i("HOT2", "" + s));
        just.subscribe(s-> Log.i("HOT2", "" + s));

        Observable<Integer> just2 = Observable.create(source->source.onNext(random.nextInt()));
        just2.subscribe(s-> Log.i("HOT2", "" + s));
        just2.subscribe(s-> Log.i("HOT2", "" + s));

    }

    public void convertColdInHotObservable3(){

        Random random = new Random();
        Observable<Integer> just = Observable.create(source -> source.onNext(random.nextInt()));
        ConnectableObservable<Integer> publish = just.publish();
        publish.subscribe(s-> Log.i("HOT3", "" + s));
        publish.subscribe(s-> Log.i("HOT3", "" + s));
        publish.connect();

    }


    public void convertColdInHotObservable4(){

        Random random = new Random();
        Observable<Integer> just = Observable.create(source -> {
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(sLong -> {
                        int value = random.nextInt();
                        Log.i("HOT4","Emitted data: " + value);
                        source.onNext(value);
                    });
        });

        // Simple same Observable which we are using only I added a one thing now this will produce data after every one second.
        ConnectableObservable<Integer> publish = just.publish();
        publish.connect();

        // Hot observable start emitting data and our new subscribers will subscribe after 2 second.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publish.subscribe(s -> System.out.println(s));
        publish.subscribe(s -> System.out.println(s));

    }

    /**Hot Observable with which any subscriber subscribed and get all previous values,
     * which already emitted by this Hot Observable plus new values and all values should be synced.
     * So to tackle that scenario we have a one very simple method. That is called replay().
     * Only we need to call that method.*/
    public void convertColdInHotObservable5(){
        Random random = new Random();
        Observable<Integer> just = Observable.create(
                source -> {
                    Observable.interval(500, TimeUnit.MILLISECONDS)
                            .subscribe(aLong -> {
                                int value = random.nextInt();
                                System.out.println("Emitted data: " + value);
                                source.onNext(value);
                            });
                }
        );
        ConnectableObservable<Integer> publish = just.replay();
        publish.connect();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publish.subscribe(s -> System.out.println("Subscriber 1: "+s));
        publish.subscribe(s -> System.out.println("Subscriber 2: "+s));

    }

    /**Hot Observable which only start data emission when first subscriber subscribed to that Hot
     * observable and should stop when all subscriber unsubscribed to that Hot Observable.
     Again this one is really simple to achieve.*/
    public void convertColdInHotObservable6(){

        Observable<Long> observable = Observable.interval(500, TimeUnit.MILLISECONDS).publish().refCount();

        Consumer<Long > firstSubscriber = s -> System.out.println("Subscriber 1: "+s);
        Consumer<Long > secondSubscriber = s -> System.out.println("Subscriber 2: "+s);

        Disposable subscribe1 = observable.subscribe(firstSubscriber);
        Disposable subscribe2 = observable.subscribe(secondSubscriber);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscribe1.dispose();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscribe2.dispose();

        Consumer<Long > thirdSubscriber = s -> System.out.println("Subscriber 3: "+s);
        Disposable subscribe3 = observable.subscribe(thirdSubscriber);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscribe3.dispose();

        Log.i("HOT6", "Finish");

    }






}
