package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeViewModel extends ViewModel {
    public WalletChargeViewModel() {
        init();
    }

    private void init() {
        aliSelect.set(R.mipmap.wallet_charge_select);
        wxSelect.set(R.mipmap.wallet_charge_unselect);
    }

    public ObservableField<String> moneyText = new ObservableField<>();
    public ObservableInt aliSelect = new ObservableInt();
    public ObservableInt wxSelect = new ObservableInt();

    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.wallet_charge_ali:
                aliSelect.set(R.mipmap.wallet_charge_select);
                wxSelect.set(R.mipmap.wallet_charge_unselect);
                break;
            case R.id.wallet_charge_wx:
                aliSelect.set(R.mipmap.wallet_charge_unselect);
                wxSelect.set(R.mipmap.wallet_charge_select);
                break;
            case R.id.wallet_charge_yes:
                doSubmit();
                break;
            default:
                break;
        }
    }

    public RelayCommand<String> relayCommand = new RelayCommand<String>(text -> {
       moneyText.set(text);
    });

    private void doSubmit() {
//        getProgressDialog().show();
        getToast().show(moneyText.get());
    }
}
