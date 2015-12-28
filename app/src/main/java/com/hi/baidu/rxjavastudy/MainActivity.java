package com.hi.baidu.rxjavastudy;

import android.app.Activity;
import android.os.Bundle;

import com.hi.baidu.rxjavasample.FirstSample;
import com.hi.baidu.rxjavasample.MemoryDiskNet;
import com.hi.baidu.rxjavasample.SecondSample;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstSample.printHelloworld();
        FirstSample.printByJust();
        FirstSample.simplifySubscriberWithAction();

        SecondSample.printByMap();
        SecondSample.printListByFrom();
        SecondSample.printListByMapFrom();
        SecondSample.printListByFlatMap();
        SecondSample.printListByFlatMapUseFuction();
        SecondSample.printStringsByFilter();
        SecondSample.printStringsByTake();
        SecondSample.printStringsByDoOnNext();
        SecondSample.printWithLift();
        SecondSample.printWithCompose();

//        ThirdSample.handleError();

//        SchedulerSample.testObserveOn();
//        SchedulerSample.loadImage();

        MemoryDiskNet.testMemoryDiskNet();
    }

}
