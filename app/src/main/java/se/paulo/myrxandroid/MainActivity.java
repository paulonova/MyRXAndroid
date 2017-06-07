package se.paulo.myrxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//http://www.uwanttolearn.com/android/dialogue-rx-observable-developer-android-rxjava2-hell-part5/

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RXConvertAnyDataCH4 rxExamples;
    RxExampleChapt3 rxExampleChapt3;
    RxObservableChapt6 rxObservableChapt6;
    RxObservableChapt7 rxObservableChapt7;
    RxObservableChapt7a rxObservableChapt7a;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.txtViewShowResults);

        user = new User();

        rxExampleChapt3 = new RxExampleChapt3();
//        rxExampleChapt3.rxExampleChapter3();

//        rxExamples = new RXConvertAnyDataCH4();
//        rxExamples.usingJustToConvertObjToObservable();
//        rxExamples.usingJustToConvertObjToObservable2();
//        Log.i("VALIDATE", "" + rxExamples.validate("Paulo", "01234"));
//        rxExamples.usingFromIterable();
//        rxExamples.usingFromArray();
//        rxExamples.usingCreateObservable();
//        rxExamples.usingObservableRange2();
//        rxExamples.usingObservableInterval();
//        rxExamples.usingObservableTimer();
//        rxExamples.usingDefer("Paulo", 27);

        /**convert any function as a Observable*/
//        rxExamples.scale(10, 4);

//        rxObservableChapt6 = new RxObservableChapt6();
//        rxObservableChapt6.usingColdObservable();
//        rxObservableChapt6.usingHotObservable();
//        rxObservableChapt6.maybeColdObservable();
//        rxObservableChapt6.convertColdInHotObservable3();
//        rxObservableChapt6.convertColdInHotObservable4();
//        rxObservableChapt6.convertColdInHotObservable5();
//        rxObservableChapt6.convertColdInHotObservable6();

        rxObservableChapt7a = new RxObservableChapt7a();

//        rxObservableChapt7a.usingObserver(); //Never exit..
//        rxObservableChapt7a.usingObserverDisposable();
//        rxObservableChapt7a.usingObserverOnError();
//        rxObservableChapt7a.usingObserverOnComplete();
//        rxObservableChapt7a.usingObserverExample();
        rxObservableChapt7a.usingObserverExampleShort();





    }






}
