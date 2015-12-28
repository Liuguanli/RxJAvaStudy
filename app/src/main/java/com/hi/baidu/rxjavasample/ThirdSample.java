package com.hi.baidu.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by liuguanli on 15/12/26.
 *
 * handleError
 *
 */
public class ThirdSample {

    private static final String TAG = "ThirdSample";

    public static void handleError() {
        Observable.just("handel error").map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return Integer.valueOf(s);
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"onError->" + e);
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG,"onNext->" + o);
            }
        });
    }
}
