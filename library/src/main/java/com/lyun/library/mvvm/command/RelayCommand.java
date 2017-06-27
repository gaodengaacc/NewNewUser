package com.lyun.library.mvvm.command;

import com.lyun.library.mvvm.command.consumer.Consumer0;
import com.lyun.library.mvvm.command.function.Function0;

import io.reactivex.functions.Consumer;

public class RelayCommand<T> {

    private Consumer0 execute0;
    private Consumer<T> execute;

    private Function0<Boolean> canExecute0;

    public RelayCommand(Consumer0 execute) {
        this.execute0 = execute;
    }


    public RelayCommand(Consumer<T> execute) {
        this.execute = execute;
    }

    /**
     * @param execute     callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    public RelayCommand(Consumer0 execute, Function0<Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * @param execute     callback for event,this callback need a params
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    public RelayCommand(Consumer<T> execute, Function0<Boolean> canExecute0) {
        this.execute = execute;
        this.canExecute0 = canExecute0;
    }


    public void execute() {
        if (execute0 != null && canExecute0()) {
            try {
                execute0.accept();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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


    public void execute(T parameter) {
        if (execute != null && canExecute0()) {
            try {
                execute.accept(parameter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
