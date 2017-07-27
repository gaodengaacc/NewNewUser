package com.lyun.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.AppApplication;
import com.lyun.user.Constants;
import com.lyun.user.EventBusMessage.EventProgressMessage;
import com.lyun.user.EventBusMessage.EventToastMessage;
import com.lyun.user.EventBusMessage.homefragment.EventPickMessage;
import com.lyun.user.EventBusMessage.homefragment.EventTranslationOrderMessage;
import com.lyun.user.R;
import com.lyun.user.activity.WaitingForTranslatorActivity;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.databinding.FragmentHomeBinding;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.viewmodel.HomeFragmentViewModel;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;
import com.lyun.user.viewmodel.WalletMainPopViewModel;
import com.lyun.utils.ACache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class HomeFragment extends MvvmFragment<FragmentHomeBinding, HomeFragmentViewModel> {

    private HomeFragmentViewModel mHomeFragmentViewModel;

    //    private LanguagePickerDialog mLanguagePickerDialog;
    private LanguagePickerDialogViewModel mLanguagePickerDialogViewModel;
    private WalletMainPopViewModel mPopViewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHomeFragmentViewModel.unRegisterEventBus();
    }

    @NonNull
    @Override
    protected HomeFragmentViewModel createViewModel() {
        mHomeFragmentViewModel = new HomeFragmentViewModel().setPropertyChangeListener(this);
        String languageStr = ACache.get(AppApplication.getInstance()).getAsString(Constants.Cache.SUPPORT_LANGUAGES);
        List<FindLanguageResponse> languageResponses = new Gson().fromJson(languageStr == null ? WalletMainPopViewModel.defaultLanguageCache : languageStr, new TypeToken<List<FindLanguageResponse>>() {
        }.getType());
        mPopViewModel = new WalletMainPopViewModel(getContext(), languageResponses);
        if (languageResponses != null && languageResponses.size() > 0){
            mHomeFragmentViewModel.selectText.set(languageResponses.get(0).getName());
            mHomeFragmentViewModel.mCurrentLanguage.set(languageResponses.get(0));
        }
        return mHomeFragmentViewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPickLanguage(EventPickMessage message) {
        if (message.getMessage())
            mPopViewModel.showAsDropDown(getFragmentViewDataBinding().selectLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTranslationOrderGenerated(EventTranslationOrderMessage message) {
        Intent intent = new Intent(getActivity(), WaitingForTranslatorActivity.class);
        intent.putExtra(TranslationOrder.ORDER_ID, message.getMessage().getOrderId());
        intent.putExtra(TranslationOrder.ORDER_TYPE, message.getMessage().getOrderType());
        intent.putExtra(TranslationOrder.TARGET_LANGUAGE, message.getMessage().getTargetLanguage());
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTranslationOrderGenerateFail(EventToastMessage message) {
        Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void progressDialogShow(EventProgressMessage message) {
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
