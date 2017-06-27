package com.lyun.library.mvvm.observable.util;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableByte;
import android.databinding.ObservableChar;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.databinding.ObservableShort;

/**
 * 针对有无论value是否变化都调用OnPropertyChangedCallback的需求
 * 当value没有变化时，主动调用notifyChange()
 * Created by ZHAOWEIWEI on 2017/2/23.
 */

public class ObservableNotifier {

    public static void alwaysNotify(ObservableBoolean observable, boolean value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableChar observable, char value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableByte observable, byte value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableInt observable, int value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableDouble observable, double value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableFloat observable, float value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableLong observable, long value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static void alwaysNotify(ObservableShort observable, short value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

    public static <T> void alwaysNotify(ObservableField<T> observable, T value) {
        if (observable.get() == value) {
            observable.notifyChange();
        } else {
            observable.set(value);
        }
    }

}
