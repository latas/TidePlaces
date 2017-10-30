package co.tide.tideplaces;

import android.support.annotation.NonNull;

import co.tide.tideplaces.rxscheduler.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;


public class BaseTest {
    protected final TestScheduler testScheduler = new TestScheduler();

    protected final BaseSchedulerProvider schedulersProvider = new BaseSchedulerProvider() {

        @NonNull
        @Override
        public Scheduler io() {
            return testScheduler;
        }

        @NonNull
        @Override
        public Scheduler ui() {
            return testScheduler;
        }

        @NonNull
        @Override
        public Scheduler computation() {
            return testScheduler;
        }

    };


}
