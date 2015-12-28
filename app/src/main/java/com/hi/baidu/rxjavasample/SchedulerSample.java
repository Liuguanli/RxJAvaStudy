package com.hi.baidu.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liuguanli on 15/12/26.
 */
public class SchedulerSample {

    private static final String TAG = "SchedulerSample";

    public static void testObserveOn() {
        Observable.just(1).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).map(new Func1<Integer, Object>() {
            @Override
            public Object call(Integer integer) {
                Log.i(TAG, "map1 call 所在线程->" + Thread.currentThread().toString());
                return integer;
            }
        }).observeOn(Schedulers.newThread()).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object o) {
                Log.i(TAG, "map2 call 所在线程->" + Thread.currentThread().toString());
                return o;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, "subscriber call 所在线程->" + Thread.currentThread().toString());
                Log.i(TAG, o.toString());
            }
        });
    }

    public static void loadImage() {
        Observable.just("图片的md5").subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                String url = "请求url+" + s;
                return new Object();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, "获取图片对象，并在主线程中加载");
            }
        });
    }

}
