package se.paulo.myrxandroid;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/** * Created by Paulo Vila Nova on 2017-05-27.
 */

public class RXConvertAnyDataCH4 {

    public static boolean isValid = true;


    /**1. just():
     By using this method you can convert any object(s) into Observable that emit that object(s).*/
    public void usingJustToConvertObjToObservable(){

        String data = "Madre Mia";
        double count = 10.5;
        Observable.just(data).subscribe(s -> Log.i("ConvertData", "" + s));  //Consumer
        Observable.just(count).subscribe(s -> Log.i("ConvertData", "nr: " + s));

    }

    /**If you have more then one data objects you can use same API like shown below.*/
    public void usingJustToConvertObjToObservable2(){

        String data2 = "Hello World";
        Integer i = 4500;
        Boolean b = true;
        double c = 20.55;
        Observable.just(data2,i,b, c).subscribe(s -> Log.i("ConvertData2", "" + s));

        /**Maximum you can use 10 data objects in this API.*/
        Observable.just(1,2,3,4,5,6,7,8,9,10).subscribe(s -> Log.i("ConvertData2", "" + s));
    }



    public boolean validate(String username, String password) {

        Observable.just(username, password)
                .subscribe(s -> {
                   if(!(s != null && !s.isEmpty() && s.length() > 3))
                       throw new RuntimeException();
                   }, throwable -> isValid = false);

        return isValid;

    }



    /**fromIterable... */

    public void usingFromIterable(){

        List<Tasks> tasks = Arrays.asList(new Tasks(1,"description"),
                new Tasks(2,"description"),new Tasks(4,"description"),
                new Tasks(3,"description"),new Tasks(5,"description"));

        Observable.fromIterable(tasks)
                .forEach(task -> Log.i("FROM", "" + task.toString()));
    }


    private static class Tasks {
        int id;
        String description;

        public Tasks(int id, String description) {
            this.id = id;this.description = description;
        }

        @Override
        public String toString() {
            return "Tasks{" + "id=" + id + ", description='" + description + '\'' + '}';
        }
    }

    /**fromArray... */
    public void usingFromArray(){
        Integer[] values = {1,2,3,4,5};
        Observable.fromArray(values)
                .subscribe(v -> Log.i("FROM", v + " "));

    }

    /**Observable.create()*/
    public void usingCreateObservable(){

        final int a = 3, b = 5, c = 9;

        Observable me = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(a);
                observableEmitter.onNext(b);
                observableEmitter.onNext(c);
                observableEmitter.onComplete();
            }
        });

        me.subscribe(s -> Log.i("CREATE", "" + s));

    }

    /**Observable.range()
     * That is just like a for loop*/
    public void usingObservableRange(){
        Observable.range(1, 10)
                .subscribe(s -> Log.i("RANGE", s + " "));
    }

    public void usingObservableRange2(){
        List<String> names = Arrays.asList("Paulo", "David", "Kelli", "Leila");
        for (int i = 0; i < names.size() ; i++) {
            if(i%2 == 0) continue;
            Log.i("RANGE", "" + names.get(i));
        }

        //OR
        Observable.range(0, names.size())
                .filter(s -> s%2 == 1) //Filer take of the element
                .subscribe(s -> Log.i("RANGE", "" + names.get(s)));

    }

    public void usingObservableInterval(){
        String greeting = "Hello";

        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .subscribe(s -> Log.i("INTERVAL", "" + greeting));

    }


    /**One more good API. In program if I want some thing will called after one second I can use
     * timer Observable as shown below.*/
    public void usingObservableTimer(){
        String greeting = "Hello";

        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(s -> Log.i("INTERVAL", "" + greeting));

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


    /**This is useful specially in mocking. This create Observable that emit nothing and complete.
     * I am showing you one example in which if tests are running then send me mock data else the real one.*/
    public void usingObservableEmpty(boolean isMock) throws InterruptedException{
        hey(false).subscribe(o -> Log.i("", "" + o));
    }

    private static Observable hey(boolean isMock) {
        return isMock ? Observable.empty() : Observable.just(1, 2, 3, 4);
    }


    /**When you use defer basically what happen Observable only created when you will subscribe
     * so its mean by using this I will get my expected result.*/
    public Observable usingDefer(String user, int age){
//        return Observable.just(user, age);

        return Observable.defer(() -> Observable.just(user, age));

    }

    /**To convert any function as a Observable - look at the MainActivity*/
    public void scale(int width, int height){

       Observable.just(width/height*.3f)
                .subscribe(value -> Log.i("SCALE", "" + value));

    }


}

