package com.hi.baidu.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by liuguanli on 15/12/26.
 */
public class FirstSample {

    private static final String TAG = "FirstSample";

    public static void printHelloworld() {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };

        myObservable.subscribe(mySubscriber);
    }

    public static void printByJust() {

        Observable<String> myObserverable = Observable.just("hello,world 哈哈");

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };

        myObserverable.subscribe(mySubscriber);
    }

    public static void simplifySubscriberWithAction() {

        Action1<String> onNextAaction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        };
        Observable<String> myObserverable = Observable.just("simplifySubscriberWithAction");
        myObserverable.subscribe(onNextAaction);

        Observable.just("代码精简版的simplifySubscriberWithAction").subscribe(onNextAaction);

    }

}
