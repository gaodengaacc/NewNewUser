package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.BR;
import com.lyun.user.Constants;
import com.lyun.user.R;
import com.lyun.user.api.response.LawWorldResponse;
import com.lyun.user.model.LawWorldModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class LawWorldViewModel extends ViewModel implements LawWorldCardViewModel.OnClickListener {

    public final ObservableList<LawWorldCardViewModel> items = new ObservableArrayList<>();
    public final ItemBinding<LawWorldCardViewModel> itemView = ItemBinding.of(BR.mvvm, R.layout.item_law_world);

    public final ObservableInt currentItem = new ObservableInt(0);

    public final ObservableInt avatarPlaceHolder = new ObservableInt(R.mipmap.ic_avatar_lawyer_male);
    public final ObservableField<String> avatar = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();

    private int currentPage = 0;
    private int nextPage = 0;
    private int totalPages = 1;

    @WatchThis
    public final ObservableField<LawWorldResponse> navigateDetail = new ObservableField<>();

    public LawWorldViewModel() {
        queryLawyerList(0);
    }

    private boolean dataReady = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!dataReady)
            queryLawyerList(nextPage);
    }

    private boolean inProcess = false;

    protected void queryLawyerList(int page) {

        if (inProcess || nextPage >= totalPages) {
            return;
        }

        inProcess = true;

        new LawWorldModel()
                .queryLawyerList(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listAPIResult -> {
                    if (page == 0) {
                        items.clear();
                    }

                    inProcess = false;

                    if (listAPIResult.isSuccess()) {

                        currentPage = nextPage;
                        nextPage++;
                        totalPages = listAPIResult.getContent().getPagecount();

                        for (LawWorldResponse response : listAPIResult.getContent().getData()) {
                            items.add(new LawWorldCardViewModel(this, response));
                        }
                        if (items.size() > 1)
                            currentItem.set(1);
                        dataReady = true;
                    }
                }, throwable -> inProcess = false);
    }

    public final RelayCommand<Integer> onPageSelected = new RelayCommand<>(page -> {

        avatarPlaceHolder.set("女".equals(items.get(page).data.get().getRealSex()) ? R.mipmap.ic_avatar_lawyer_female : R.mipmap.ic_avatar_lawyer_male);
        avatar.set(Constants.IMAGE_BASE_URL + items.get(page).data.get().getUserImg());
        name.set(items.get(page).data.get().getRealName());

        if (page + 4 <= items.size()) {
            queryLawyerList(nextPage);
        }
    });

    @Override
    public void onClick(LawWorldResponse card) {
        ObservableNotifier.alwaysNotify(navigateDetail, card);
    }
}
