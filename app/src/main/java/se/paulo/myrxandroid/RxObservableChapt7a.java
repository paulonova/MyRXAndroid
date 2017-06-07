package se.paulo.myrxandroid;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/** * Created by Paulo Vila Nova on 2017-06-07.
 */

public class RxObservableChapt7a {

    public static Disposable disposable;
    private static Consumer<String > consumer = System.out::print;
    //private static Consumer<String > consumer2 = s->{};
    private static Consumer<Throwable> error = System.out::print;
    private static Action complete = ()-> System.out.println("onComplete");
    private static Disposable d;
    private static Consumer<Disposable> disposable2 = disposable -> d = disposable;



    public void usingObserver(){

        Observer<Object> observer = new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable disposable) {
                RxObservableChapt7a.disposable = disposable;
            }
            @Override
            public void onNext(Object o) {
                System.out.println("onNext called");
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError called. Die due to reason: "+throwable.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("onComplete: Die with natural death");
            }
        };
        getObservable().subscribe(observer);  // Observer is married with Observable..

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("OBSERVER", "disposable.isDisposed(): " + disposable.isDisposed()); //false = not divorced..
        }

    }


    /**here again code is same only difference in while loop. This time I added a one count variable.
     *  So as I got data from Observable three time’s I will call dispose.
     *  Its mean I want divorce from Observable.*/
    public void usingObserverDisposable() {

        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                RxObservableChapt7a.disposable = disposable;
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext called");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError called. Die due to reason: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: Die with natural death");
            }
        };

        getObservable().subscribe(observer);

        int count = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("disposable.isDisposed(): " + disposable.isDisposed());

            count++;
            if (count == 3)
                disposable.dispose();
        }


    }


    private static Observable<Object> getObservable() {
        return Observable.create(observableEmitter -> {
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(aLong -> observableEmitter.onNext(new Object()));
        });
    }


    /**Now here again I am using same code except the method from which we are getting Observable.
     * This Observable will send data 4 time and after that will die due to some reason but here
     * I am creating that reason forcefully. So we can get the concept of onError().*/
    public void usingObserverOnError() {

        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                RxObservableChapt7a.disposable = disposable;
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext called");
                System.out.println("disposable.isDisposed(): " + disposable.isDisposed());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError called. Die due to reason: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: Die with natural death");
            }
        };

        getObservable2().subscribe(observer);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("disposable.isDisposed(): " + disposable.isDisposed());
        }

    }


    private static Observable<Object> getObservable2() {
        return Observable.create(observableEmitter -> {
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onError(new RuntimeException("Die due and went to heaven"));
        });
    }




    public void usingObserverOnComplete() {

        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                RxObservableChapt7a.disposable = disposable;
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext called");
                System.out.println("disposable.isDisposed(): " + disposable.isDisposed());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError called. Die due to reason: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: Die with natural death");
            }
        };

        getObservable3().subscribe(observer);

    }

    private static Observable<Object> getObservable3() {
        return Observable.create(observableEmitter -> {
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onNext(new Object());
            observableEmitter.onComplete();
        });
    }


    public void usingObserverExample() {

        List<String> strings = Arrays.asList("A", "B", "C", "D");
        Observable.fromIterable(strings)
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull String string) {
                        System.out.println("onNext: "+string);

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        System.out.println("onError");

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");

                    }
                });

    }

    /**In functionality both above examples are same but you can see this time I only use two lines
     * of code, and before that its a very long code. So now I am going to share with you all my
     * Functional Interfaces and how you can use in your applications.*/
    public void usingObserverExampleShort() {
        List<String> strings = Arrays.asList("A", "B", "C", "D");
        Observable.fromIterable(strings)
//                .subscribe(s -> Log.i("SHORT_EXAMPLE","" + s));
                .subscribe(consumer, error, complete, disposable2);



    }




}