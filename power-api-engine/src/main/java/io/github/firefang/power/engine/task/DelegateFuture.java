package io.github.firefang.power.engine.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.github.firefang.power.engine.step.StepContext;

/**
 * @author xinufo
 *
 */
public class DelegateFuture<T> implements Future<T> {
    private Future<T> future;
    private ForkJoinPool pool;
    private StepContext stepContext;

    public DelegateFuture(Future<T> future, ForkJoinPool pool, StepContext stepContext) {
        this.future = future;
        this.pool = pool;
        this.stepContext = stepContext;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        stepContext.setCancelled(true);
        if (mayInterruptIfRunning) {
            pool.shutdownNow();
            return true;
        }
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

}
