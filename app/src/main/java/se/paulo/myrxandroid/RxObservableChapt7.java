package se.paulo.myrxandroid;

/** * Created by Paulo Vila Nova on 2017-05-29.
 */

public class RxObservableChapt7 {


    //void onSubscribe(Disposable var1);

//    Any time when you attached Observer with Observable you will get Disposable object. That has a very simple API as shown below.

//    public interface Disposable {
//        void dispose();
//
//        boolean isDisposed();
//    }


// So dispose() it is just like you are no more interested in this Observable changes.
// So any time when I want to leave Observable I always call  my Disposable var1;.
// var1.dispose() method. That is just like a divorce between me(Observer) and Observable.
// After that any event occur in Observable I don’t care. I will not be update or conveyed by that change.
// That is very useful on some places specially in Android I will show you later.
//    Second is isDisposed(), that method only useful if I am confused like I want a data from Observable but I
// have a feeling may be I already divorced, so I can check am I divorced or not. Vice versa also like I am not sure,
// am I already divorced. For that I can check by using this method, and if I got false in a method call result its
// mean I am not divorced so I need to call dispose() method.


//    void onNext(T var1);
//    This method is used when I am subscribed to Observable, and Observable want’s to inform me there is a change or new data.
//    I think I will explain differently. When Observable want’s to marry with me. He exposed me one API onSubscribe(Observer).
// Then I accepted his proposal of marry by going inside of his onSubscribe() API but important point I also got the Disposable
// which means I have an option to gave a divorce to Observable at any time. Now as we marry Observable always inform me about
// any change which will occur in his data or event stream. Now at that time Observable used my onNext([any data]) method. So in
// simple words when Observable have any change in his data he always inform to [Me, Developers] by using my onNext(T data) method of mine.


//    void onError(Throwable var1);
//    This API is really critical and emotional for me. Any time when Observable faced any issue he will die
// and inform me by using my onError(Throwable var1) API. In Throwable he always informed me why or what type
// of issue he faced before died. Its mean any time when onError() called after that disposable isDispose()
// method always give me true. So its mean sometimes when I never ask for divorce but Observable faced some
// issue and he died, so I can check by using isDispose() which will return me true.
//

//    void onComplete();
//    This API is again critical and emotional for me. Any time when Observable is ready to die or want to
// give me a divorce. He always inform me by using onComplete(). As Observable die or give me divorce my
// Disposable again work in a same way as we already discuss in onError() API. I hope currently everything is clear.


}
