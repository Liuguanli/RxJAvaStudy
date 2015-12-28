package com.hi.baidu.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuguanli on 15/12/26.
 * 变换：操作符的使用
 * 例如 访问资源时  通过map变换为每个Observable添加访问的前缀
 * <p/>
 * 一个Observable可以使用多次map等变换函数，这样的目的是 减少subscrib的操作。 让subscriber做的事情越少越好
 */
public class SecondSample {

    private static final String TAG = "SecondSample";

    public static void printByMap() {
        Observable.just("printByMap").map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return "use map to print " + s;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, o.toString());
            }
        });
    }

    public static void printListByFrom() {
        String[] ss = new String[]{"url1", "url2", "url3"};
        Observable.from(ss).subscribe(new Action1<String>() {
            @Override
            public void call(String stirng) {
                Log.i(TAG, stirng);
            }
        });
    }

    public static void printListByMapFrom() {
        String[] ss = new String[]{"url1", "url2", "url3"};
        Observable.from(ss).map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return "use map to print " + s;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, stirng.toString());
            }
        });
    }

    public static void printListByFlatMap() {
        String[] ss = new String[]{"url1", "url2", "url3"};
        Observable.just(ss).flatMap(new Func1<String[], Observable<?>>() {
            @Override
            public Observable<?> call(String[] strings) {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, "printListByFlatMap " + stirng.toString());
            }
        });
    }

    public static void printListByFlatMapUseFuction() {
        Observable.just("hello world").flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String string) {
                return getTitle(string);
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, "printListByFlatMap " + stirng.toString());
            }
        });
    }

    public static void printStringsByFilter() {
        String[] ss = new String[]{"1", "2", "3"};
        Observable.just(ss).flatMap(new Func1<String[], Observable<?>>() {
            @Override
            public Observable<?> call(String[] strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return o != null && !"2".equals(o.toString());
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, "printStringsByFilter " + stirng.toString());
            }
        });
    }


    public static void printStringsByTake() {
        String[] ss = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        Observable.just(ss).flatMap(new Func1<String[], Observable<?>>() {
            @Override
            public Observable<?> call(String[] strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return o != null && !"2".equals(o.toString());
            }
        }).take(5).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, "printStringsByFilter " + stirng.toString());
            }
        });
    }

    public static void printStringsByDoOnNext() {
        String[] ss = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        Observable.just(ss).flatMap(new Func1<String[], Observable<?>>() {
            @Override
            public Observable<?> call(String[] strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return o != null && !"2".equals(o.toString());
            }
        }).take(5).doOnNext(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, "将" + o.toString() + "保存到磁盘");
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object stirng) {
                Log.i(TAG, "printStringsByFilter " + stirng.toString());
            }
        });
    }

    /**
     * 针对事件序列的处理和再发送
     * lift 可以当做AOP来使用
     * RxJava不建议开发者自己定义Operator来直接使用lift，而是使用以后的map等方法来使用。
     */
    public static void printWithLift() {
        Observable.just("test lift").lift(new Observable.Operator<Object, String>() {
            @Override
            public Subscriber<? super String> call(final Subscriber<? super Object> subscriber) {

                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(String s) {
                        subscriber.onNext("经过之后的" + s);
                    }
                };
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, o.toString());
            }
        });
    }

    /**
     * compose利用传入的Transformer对象的call方法将多个lift封装。
     */
    public static void printWithCompose() {
        Observable.just("compose").compose(new Observable.Transformer<String, Object>() {
            @Override
            public Observable<Object> call(Observable<String> stringObservable) {
                return stringObservable.lift(new Observable.Operator<Object, String>() {
                    @Override
                    public Subscriber<? super String> call(final Subscriber<? super Object> subscriber) {
                        return new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                subscriber.onNext("第一个lift->" + s);
                            }
                        };
                    }
                }).lift(new Observable.Operator<Object, Object>() {
                    @Override
                    public Subscriber<? super Object> call(final Subscriber<? super Object> subscriber) {
                        return new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {
                                subscriber.onNext("第二个lift->" + o.toString());
                            }
                        };
                    }
                });
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i(TAG, "最后一步->" + o.toString());
            }
        });
    }

    private static Observable<String> getTitle(String string) {
        return Observable.just("getTitle: " + string);
    }


}
