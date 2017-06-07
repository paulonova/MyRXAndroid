package se.paulo.myrxandroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/** * Created by Paulo Vila Nova on 2017-05-27.
 */

public class RxExampleChapt3 {


    public void rxExampleChapter3(){

        Integer[] data = {1,2,3,4};

        List<User> users = new ArrayList<>();
        users.add(new User("A",false));
        users.add(new User("B",true));
        users.add(new User("C",true));
        users.add(new User("D",false));
        users.add(new User("E",false));

        List<String> list = new ArrayList<>();
        list.add("H");
        list.add("e");
        list.add("l");
        list.add("l");
        list.add("o");
        list.add(" ");
        list.add("W");
        list.add("o");
        list.add("r");
        list.add("l");
        list.add("d");

        /**In Java 8: list.stream()
         * In Android: Observable.fromIterable(list)*/
        Observable.fromIterable(list)
                .filter(s -> !s.equals("l"))
                .forEach(s-> Log.i("Android",s)); // Android

        Observable.fromArray(data)
                .map(value -> value * value)
                .map(value -> Integer.toString(value))
                .forEach(string -> Log.i("Android", string));


        //without RX
//        for (User user : users) {
//            if(user.status){
//                user.username = user.username+ " Online";
//            }else {
//                user.username = user.username+ " Offline";
//            }
//            Log.i("Android",user.username);
//        }

        //With RX By using two streams.
        Observable.fromIterable(users)
                .filter(user -> user.status)
                .map(user -> user.username + " Online")
                .subscribe(user -> Log.i("Android", user ));

        Observable.fromIterable(users)
                .filter(user -> !user.status)
                .map(user -> user.username + " Offline")
                .subscribe(user -> Log.i("Android", user ));

        //By using If else in map:
        Observable.fromIterable(users)
                .map(user -> {
                    if (user.status) {
                        user.username = user.username + " Online";
                    } else {
                        user.username = user.username + " Offline";
                    }
                    return user;
                })
                .subscribe(user -> Log.i("Android", user.toString()));


        //If we have some nested loops in our code.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("j ");
            }
            System.out.println("i");
        }

        //to

        Observable.range(0, 10)
                .doAfterNext(i-> System.out.println("i"))
                .flatMap(integer -> Observable.range(0, 10))
                .doOnNext(i -> System.out.print("j "))
                .subscribe();



    }


}
