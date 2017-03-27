package com.lyun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.BaseApplication;
import com.lyun.library.R;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Field;

public class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // LeakCanary
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    /**
     * Override onDetach to fix this bug:
     * java.lang.IllegalStateException: Activity has been destroyed
     * at android.support.v4.app.FragmentManagerImpl.enqueueAction(FragmentManager.java:1854)
     * at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:643)
     * at android.support.v4.app.BackStackRecord.commitAllowingStateLoss(BackStackRecord.java:608)
     * at com.lyun.lawyer.im.session.activity.TranslationMessageActivity.switchContent(TranslationMessageActivity.java:278)
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
