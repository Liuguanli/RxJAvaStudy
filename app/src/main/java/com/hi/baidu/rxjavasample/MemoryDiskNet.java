package com.hi.baidu.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuguanli on 15/12/27.
 */
public class MemoryDiskNet {

    private static final String TAG = "MemoryDiskNet";

    private static String memory;
    private static String disk;
    private static String net;

    private static Observable<String> memory() {
        final Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "in the memory before onNext");
                subscriber.onNext(memory);
                Log.i(TAG, "in the memory after onNext");
                subscriber.onCompleted();
            }
        });
        return observable.compose(logSource("MEMORY"));
    }

    private static Observable<String> disk() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(disk);
                subscriber.onCompleted();
            }
        });
        return observable.doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "disk::call->" + s);
                memory = s;
            }
        }).compose(logSource("DISK"));
    }

    private static Observable<String> network() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Log.i(TAG, "in the net");
                new Runnable(){

                    @Override
                    public void run() {
                        subscriber.onNext(disk = new String("network"));
                        subscriber.onCompleted();
                    }
                    //  这里明天继续优化 写出来！！！
                }.run();

            }
        });
        return observable.doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "net::call->" + s);
                memory = s;
                disk = s;
            }
        }).compose(logSource("NET"));
    }

    public static void testMemoryDiskNet() {
        final Observable<String> source = Observable.concat(memory(), disk(), network());
        source.first(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                Log.i(TAG, "first:" + s);
                return s != null;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "subsricbe:" + s);
            }
        });

//        Observable.interval(1, TimeUnit.SECONDS).flatMap(new Func1<String, Observable<?>>() {
//            @Override
//            public Observable<?> call(String s) {
//                return source.doOnNext(new );
//            }
//        }).subscribe(new Action1<Object>() {
//            @Override
//            public void call(Object o) {
//                Log.i(TAG, "o:" + o);
//            }
//        });

    }

    private static Observable.Transformer<String, String> logSource(final String source) {
        return new Observable.Transformer<String, String>() {
            @Override
            public Observable<String> call(Observable<String> stringObservable) {
                return stringObservable.doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s == null) {
                            Log.i(TAG, source + "没有数据");
                        } else {
                            Log.i(TAG, source + "有数据");
                        }
                    }
                });
            }
        };
    }

}
