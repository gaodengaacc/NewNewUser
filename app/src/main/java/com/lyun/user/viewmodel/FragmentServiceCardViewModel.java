package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.BR;
import com.lyun.user.R;
import com.lyun.user.adapter.ServiceCardListAdapter;
import com.lyun.user.api.response.ServiceCardResponse;
import com.lyun.user.model.ServiceCardModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class FragmentServiceCardViewModel extends ViewModel {

    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示

    public final ObservableField<String> cardName = new ObservableField<>();
    public final ObservableDouble cardPrice = new ObservableDouble();

    public final ObservableInt currentPage = new ObservableInt(0);

    public final ObservableList<ServiceCardViewModel> serviceCardViewModels = new ObservableArrayList<>();
    public final ObservableField<ItemView> serviceCardView = new ObservableField<>();

    public final ObservableField<BaseRecyclerAdapter> serviceCardItemAdapter = new ObservableField<>();
    public final ObservableList<ViewModel> serviceCardItemViewModels = new ObservableArrayList<>();
    public String id;

    protected List<ServiceCardResponse> mServiceCardList = new ArrayList<>();

    @WatchThis
    public final ObservableDouble buyCard = new ObservableDouble();
    @WatchThis
    public final ObservableField<ServiceCardResponse> navigateCardDetail = new ObservableField();

    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(AppApplication.getInstance(), 3);

    public FragmentServiceCardViewModel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }

        serviceCardView.set(ItemView.of(BR.mvvm, R.layout.item_service_card));

        ServiceCardListAdapter adapter = new ServiceCardListAdapter(null, R.layout.item_service_card_item);
        serviceCardItemAdapter.set(adapter);
    }

    public boolean dataReady = false;

    @Override
    public void onResume() {
        super.onResume();
        serviceCardItemAdapter.get().notifyDataSetChanged();
        if (!dataReady)
            queryServiceCardList();
    }

    public void queryServiceCardList() {
        new ServiceCardModel()
                .queryServiceCardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listAPIResult -> {
                    if (listAPIResult.isSuccess()) {
                        mServiceCardList = listAPIResult.getContent();

                        serviceCardViewModels.clear();

                        for (ServiceCardResponse card : mServiceCardList) {

                            List<ServiceCardServiceItemViewModel> itemViewModels = new ArrayList<>();

                            // 电话咨询
                            int totalTime = Integer.parseInt(card.getTotalTime());
                            if (totalTime > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_tel_consultation, "电话咨询", totalTime + "分钟"));
                            }
                            // 资深律师咨询
                            if (card.getOnlineSeniorCounselAdviceTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_senior_counsel, "资深律师咨询", card.getOnlineSeniorCounselAdviceTimes() + "次"));
                            }

                            // 案件委托
                            if (Double.parseDouble(card.getCaseConsignTimes()) > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_case_commission, "案件委托", card.getCaseConsignTimes() + "折"));
                            }

                            //法律文书定制
                            if (card.getLegalInstrumentsDraftTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_legal_doc_customization, "法律文书定制", card.getLegalInstrumentsDraftTimes() + "次"));
                            }

                            // 律师函定制
                            if (card.getLawyerLetterDraftTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_lawyers_letter_customization, "律师函定制", card.getLawyerLetterDraftTimes() + "份"));
                            }

                            // 法律文书审核
                            if (card.getLegalInstrumentsReviewTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_legal_doc_review, "法律文书审核", card.getLegalInstrumentsReviewTimes() + "次"));
                            }

                            // 法律讲座
                            if (card.getLegalLectureTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_law_lecture, "法律讲座", card.getLegalLectureTimes() + "次"));
                            }

                            // 特色主题讲座
                            if (card.getFeaturedTopicLectureTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_featured_lectures, "特色主题讲座", card.getFeaturedTopicLectureTimes() + "次"));
                            }

                            // 合同文书翻译
                            if (card.getContractInstrumentTranslationTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_contract_translation, "合同文书翻译", card.getContractInstrumentTranslationTimes() + "次"));
                            }

                            // 海外法律服务咨询
                            if (card.getOverseasLawyerServiceConsultationTimes() > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_oversea_legal_advice, "海外法律服务咨询", card.getOverseasLawyerServiceConsultationTimes() + "次"));
                            }

                            // 海外案件委托
                            if (Double.parseDouble(card.getOverseasCaseConsignTimes()) > 0) {
                                itemViewModels.add(new ServiceCardServiceItemViewModel(R.mipmap.ic_service_item_oversea_case_commission, "海外案件委托", card.getOverseasCaseConsignTimes() + "折"));
                            }

                            serviceCardViewModels.add(new ServiceCardViewModel(card.getName(), card.getPrice(), card.getLogoImg(), card.getId(), itemViewModels));
                        }

                        int index = serviceCardViewModels.size() > 1 ? 1 : 0;
                        id = serviceCardViewModels.get(index).id;
                        currentPage.set(index);
                        onPageSelected.execute(index);
                        dataReady = true;
                    }
                });
    }

    private int mBuyCardPosition;

    public void paySuccess() {
        mServiceCardList.remove(mBuyCardPosition);
        serviceCardItemViewModels.remove(mBuyCardPosition);
        queryServiceCardList();
    }

    public final RelayCommand<Integer> onPageSelected = new RelayCommand<>(page -> {
        serviceCardItemViewModels.clear();
        serviceCardItemViewModels.addAll(serviceCardViewModels.get(page).itemViewModels);
        cardName.set(serviceCardViewModels.get(page).name.get());
        cardPrice.set(serviceCardViewModels.get(page).price.get());
        id = serviceCardViewModels.get(page).id;
    });

    public final RelayCommand onBuyCard = new RelayCommand(() -> {
        mBuyCardPosition = currentPage.get();
        if (mServiceCardList != null && mServiceCardList.size() > mBuyCardPosition) {
            ObservableNotifier.alwaysNotify(buyCard, cardPrice.get());
        }
    });

    public final RelayCommand showCardDetail = new RelayCommand(() -> {
        if (mServiceCardList != null && mServiceCardList.size() > currentPage.get()) {
            ObservableNotifier.alwaysNotify(navigateCardDetail, mServiceCardList.get(currentPage.get()));
        }
    });
}
