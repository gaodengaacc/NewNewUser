package com.lyun.library.mvvm.command;

import com.lyun.library.mvvm.command.function.Function0;

import io.reactivex.functions.Function;

public class ResponseCommand<T, R> {

    private Function0<R> execute0;
    private Function<T, R> execute1;

    private Function0<Boolean> canExecute0;

    /**
     * like {@link RelayCommand},but ResponseCommand can return result when command has executed!
     *
     * @param execute function to execute when event occur.
     */
    public ResponseCommand(Function0<R> execute) {
        this.execute0 = execute;
    }


    public ResponseCommand(Function<T, R> execute) {
        this.execute1 = execute;
    }


    public ResponseCommand(Function0<R> execute, Function0<Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }


    public ResponseCommand(io.reactivex.functions.Function execute, Function0<Boolean> canExecute0) {
        this.execute1 = execute;
        this.canExecute0 = canExecute0;
    }


    public R execute() {
        if (execute0 != null && canExecute0()) {
            try {
                return execute0.apply();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        try {
            return canExecute0.apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public R execute(T parameter) {
        if (execute1 != null && canExecute0()) {
            try {
                return execute1.apply(parameter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
