package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.ServiceCardResponse;
import com.lyun.user.eventbusmessage.cardpay.EventShowPayDialogMessage;

import org.greenrobot.eventbus.EventBus;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardDetailViewModel extends ViewModel {

    /**
     * 法律服务卡名称
     */
    public final ObservableField<String> name = new ObservableField<>();
    /**
     * 通话总时长
     */
    public final ObservableField<String> totalTime = new ObservableField<>();
    /**
     * 在线资深律师咨询次数
     */
    public final ObservableInt onlineSeniorCounselAdviceTimes = new ObservableInt();
    /**
     * 案件委托次数
     */
    public final ObservableInt caseConsignTimes = new ObservableInt();
    /**
     * 海外案件委托/线下次数
     */
    public final ObservableInt overseasCaseConsignTimes = new ObservableInt();
    /**
     * 法律文书定制次数
     */
    public final ObservableInt legalInstrumentsDraftTimes = new ObservableInt();
    /**
     * 律师函定制次数
     */
    public final ObservableInt lawyerLetterDraftTimes = new ObservableInt();
    /**
     * 法律文书审核次数
     */
    public final ObservableInt legalInstrumentsReviewTimes = new ObservableInt();
    /**
     * 法律讲座次数
     */
    public final ObservableInt legalLectureTimes = new ObservableInt();
    /**
     * 特色主题讲座次数
     */
    public final ObservableInt featuredTopicLectureTimes = new ObservableInt();
    /**
     * 合同/翻译翻译次数
     */
    public final ObservableInt contractInstrumentTranslationTimes = new ObservableInt();
    /**
     * 海外律师服务咨询次数
     */
    public final ObservableInt overseasLawyerServiceConsultationTimes = new ObservableInt();

    /**
     * 实价价格
     */
    public double price;

    /**
     * 实价价格
     */
    public String cardId;

    public final ObservableInt visibilityDomesticService = new ObservableInt();
    public final ObservableInt visibilityTotalTimes = new ObservableInt();
    public final ObservableInt visibilityOnlineSeniorCounselAdviceTimes = new ObservableInt();
    public final ObservableInt visibilityCaseConsignTimes = new ObservableInt();

    public final ObservableInt visibilityDocService = new ObservableInt();
    public final ObservableInt visibilityLegalInstrumentsDraftTimes = new ObservableInt();
    public final ObservableInt visibilityLawyerLetterDraftTimes = new ObservableInt();
    public final ObservableInt visibilityLegalInstrumentsReviewTimes = new ObservableInt();

    public final ObservableInt visibilityLectureService = new ObservableInt();
    public final ObservableInt visibilityLegalLectureTimes = new ObservableInt();
    public final ObservableInt visibilityFeaturedTopicLectureTimes = new ObservableInt();

    public final ObservableInt visibilityOverseaService = new ObservableInt();
    public final ObservableInt visibilityOverseasCaseConsignTimes = new ObservableInt();
    public final ObservableInt visibilityContractInstrumentTranslationTimes = new ObservableInt();
    public final ObservableInt visibilityOverseasLawyerServiceConsultationTimes = new ObservableInt();

    public ServiceCardDetailViewModel(ServiceCardResponse card) {
        name.set(card.getName());
        cardId = card.getId();
        // 境内服务
        totalTime.set(card.getTotalTime());
        visibilityTotalTimes.set(Integer.parseInt(totalTime.get()) > 0 ? VISIBLE : GONE);

        onlineSeniorCounselAdviceTimes.set(card.getOnlineSeniorCounselAdviceTimes());
        visibilityOnlineSeniorCounselAdviceTimes.set(onlineSeniorCounselAdviceTimes.get() > 0 ? VISIBLE : GONE);

        caseConsignTimes.set(card.getCaseConsignTimes());
        visibilityCaseConsignTimes.set(caseConsignTimes.get() > 0 ? VISIBLE : GONE);

        visibilityDomesticService.set(visibilityTotalTimes.get() == GONE &&
                visibilityOnlineSeniorCounselAdviceTimes.get() == GONE &&
                visibilityCaseConsignTimes.get() == GONE ? GONE : VISIBLE);

        // 文案服务
        legalInstrumentsDraftTimes.set(card.getLegalInstrumentsDraftTimes());
        visibilityLegalInstrumentsDraftTimes.set(legalInstrumentsDraftTimes.get() > 0 ? VISIBLE : GONE);

        lawyerLetterDraftTimes.set(card.getLawyerLetterDraftTimes());
        visibilityLawyerLetterDraftTimes.set(lawyerLetterDraftTimes.get() > 0 ? VISIBLE : GONE);

        legalInstrumentsReviewTimes.set(card.getLegalInstrumentsReviewTimes());
        visibilityLegalInstrumentsReviewTimes.set(legalInstrumentsReviewTimes.get() > 0 ? VISIBLE : GONE);

        visibilityDocService.set(visibilityLegalInstrumentsDraftTimes.get() == GONE &&
                visibilityLawyerLetterDraftTimes.get() == GONE &&
                visibilityLegalInstrumentsReviewTimes.get() == GONE ? GONE : VISIBLE);

        // 讲座课程
        legalLectureTimes.set(card.getLegalLectureTimes());
        visibilityLegalLectureTimes.set(legalLectureTimes.get() > 0 ? VISIBLE : GONE);

        featuredTopicLectureTimes.set(card.getFeaturedTopicLectureTimes());
        visibilityFeaturedTopicLectureTimes.set(featuredTopicLectureTimes.get() > 0 ? VISIBLE : GONE);

        visibilityLectureService.set(visibilityLegalLectureTimes.get() == GONE &&
                visibilityFeaturedTopicLectureTimes.get() == GONE ? GONE : VISIBLE);

        // 海外服务
        overseasCaseConsignTimes.set(card.getOverseasCaseConsignTimes());
        visibilityOverseasCaseConsignTimes.set(overseasCaseConsignTimes.get() > 0 ? VISIBLE : GONE);

        overseasLawyerServiceConsultationTimes.set(card.getOverseasLawyerServiceConsultationTimes());
        visibilityOverseasLawyerServiceConsultationTimes.set(overseasLawyerServiceConsultationTimes.get() > 0 ? VISIBLE : GONE);

        contractInstrumentTranslationTimes.set(card.getContractInstrumentTranslationTimes());
        visibilityContractInstrumentTranslationTimes.set(contractInstrumentTranslationTimes.get() > 0 ? VISIBLE : GONE);

        visibilityOverseaService.set(visibilityOverseasCaseConsignTimes.get() == GONE &&
                visibilityOverseasLawyerServiceConsultationTimes.get() == GONE &&
                visibilityContractInstrumentTranslationTimes.get() == GONE ? GONE : VISIBLE);

        price = card.getPrice();
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.card_detail_buy:
                EventBus.getDefault().post(new EventShowPayDialogMessage(999.0));
                break;
            default:
                break;
        }
    }

    public SpannableString makeTitle(String title, String subtitle) {
        SpannableString spannableString = new SpannableString(title + "  " + subtitle);
        ForegroundColorSpan titleColorSpan = new ForegroundColorSpan(Color.parseColor("#485465"));
        spannableString.setSpan(titleColorSpan, 0, title.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan subtitleColorSpan = new ForegroundColorSpan(Color.parseColor("#1f9eec"));
        spannableString.setSpan(subtitleColorSpan, title.length() + 2, title.length() + subtitle.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan subtitleTextSizeSpan = new RelativeSizeSpan(0.8f);
        spannableString.setSpan(subtitleTextSizeSpan, title.length() + 2, title.length() + subtitle.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
