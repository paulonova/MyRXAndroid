package se.paulo.myrxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;

public class RxObservableChapt5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String data = "Hello World";
        Observable.just(data).subscribe(s -> System.out.println(s));




    }
}
