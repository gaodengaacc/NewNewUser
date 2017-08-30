package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * Created by ZHAOWEIWEI on 2017/8/30.
 */

public class ServiceCardResponse extends BaseResponse {

    /**
     * id : 1
     * name : 普通卡
     * onlineLegalAdviceTime : 1
     * seniorCounselAdviceTimes : 1
     * legalInstrumentsDraftTimes : 1
     * lawyerLetterDraftTimes : 1
     * legalInstrumentsReviewTimes : 1
     * legalLectureTimes : 1
     * featuredTopicLectureTimes : 1
     * contractInstrumentTranslationTimes : 1
     * overseasLawyerServiceConsultationTimes : 1
     * markedPrice : 99
     * discount : 0
     * price : 99
     * logoImg : D:\lvtappImg\普通卡@3x.png
     * state : 0
     * createTime : null
     * creator : null
     * modifyTime : null
     * modifier : null
     * totalTime : 120
     * cardType : {"id":2,"name":"普通卡"}
     */

    private String id;
    private String name;
    private int onlineLegalAdviceTime;
    private int seniorCounselAdviceTimes;
    private int legalInstrumentsDraftTimes;
    private int lawyerLetterDraftTimes;
    private int legalInstrumentsReviewTimes;
    private int legalLectureTimes;
    private int featuredTopicLectureTimes;
    private int contractInstrumentTranslationTimes;
    private int overseasLawyerServiceConsultationTimes;
    private int markedPrice;
    private int discount;
    private int price;
    private String logoImg;
    private String state;
    private Object createTime;
    private Object creator;
    private Object modifyTime;
    private Object modifier;
    private String totalTime;
    private CardTypeBean cardType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnlineLegalAdviceTime() {
        return onlineLegalAdviceTime;
    }

    public void setOnlineLegalAdviceTime(int onlineLegalAdviceTime) {
        this.onlineLegalAdviceTime = onlineLegalAdviceTime;
    }

    public int getSeniorCounselAdviceTimes() {
        return seniorCounselAdviceTimes;
    }

    public void setSeniorCounselAdviceTimes(int seniorCounselAdviceTimes) {
        this.seniorCounselAdviceTimes = seniorCounselAdviceTimes;
    }

    public int getLegalInstrumentsDraftTimes() {
        return legalInstrumentsDraftTimes;
    }

    public void setLegalInstrumentsDraftTimes(int legalInstrumentsDraftTimes) {
        this.legalInstrumentsDraftTimes = legalInstrumentsDraftTimes;
    }

    public int getLawyerLetterDraftTimes() {
        return lawyerLetterDraftTimes;
    }

    public void setLawyerLetterDraftTimes(int lawyerLetterDraftTimes) {
        this.lawyerLetterDraftTimes = lawyerLetterDraftTimes;
    }

    public int getLegalInstrumentsReviewTimes() {
        return legalInstrumentsReviewTimes;
    }

    public void setLegalInstrumentsReviewTimes(int legalInstrumentsReviewTimes) {
        this.legalInstrumentsReviewTimes = legalInstrumentsReviewTimes;
    }

    public int getLegalLectureTimes() {
        return legalLectureTimes;
    }

    public void setLegalLectureTimes(int legalLectureTimes) {
        this.legalLectureTimes = legalLectureTimes;
    }

    public int getFeaturedTopicLectureTimes() {
        return featuredTopicLectureTimes;
    }

    public void setFeaturedTopicLectureTimes(int featuredTopicLectureTimes) {
        this.featuredTopicLectureTimes = featuredTopicLectureTimes;
    }

    public int getContractInstrumentTranslationTimes() {
        return contractInstrumentTranslationTimes;
    }

    public void setContractInstrumentTranslationTimes(int contractInstrumentTranslationTimes) {
        this.contractInstrumentTranslationTimes = contractInstrumentTranslationTimes;
    }

    public int getOverseasLawyerServiceConsultationTimes() {
        return overseasLawyerServiceConsultationTimes;
    }

    public void setOverseasLawyerServiceConsultationTimes(int overseasLawyerServiceConsultationTimes) {
        this.overseasLawyerServiceConsultationTimes = overseasLawyerServiceConsultationTimes;
    }

    public int getMarkedPrice() {
        return markedPrice;
    }

    public void setMarkedPrice(int markedPrice) {
        this.markedPrice = markedPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getCreator() {
        return creator;
    }

    public void setCreator(Object creator) {
        this.creator = creator;
    }

    public Object getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Object modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Object getModifier() {
        return modifier;
    }

    public void setModifier(Object modifier) {
        this.modifier = modifier;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public CardTypeBean getCardType() {
        return cardType;
    }

    public void setCardType(CardTypeBean cardType) {
        this.cardType = cardType;
    }

    public static class CardTypeBean {
        /**
         * id : 2
         * name : 普通卡
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
