package se.paulo.myrxandroid;

import io.reactivex.Observable;

/** * Created by Paulo Vila Nova on 2017-05-26.
 */

public class User {

    String username;
    boolean status;

    Observable getObservable(){
//        return Observable.just(username, status);
        return Observable.defer(() -> Observable.just(username, status));
    }

    public User(String username, boolean status) {
        this.username = username;
        this.status = status;
    }

    public User() {
    }
}
