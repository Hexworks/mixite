package org.codetome.hexameter.core;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

public class RxTest {

    @Test
    public void test() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext("" + i);
                }
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }
}
