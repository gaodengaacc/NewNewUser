package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/8/30
 * do()
 */

public class MyServiceCardResponse extends BaseResponse {
//    	          "id": 3,
//                "orderNo": "17899891461682892800151018",
//                "tradeTime": 1503385820000,
//                "activeStartTime": 1503385820000,
//                "activeEndTime": 1535785820000,
//                "card": {
//                    "id": "1",
//                    "name": "普通卡",
    //                "onlineLegalAdviceTime": 1,
    //                "seniorCounselAdviceTimes": 1,
    //                "legalInstrumentsDraftTimes": 1,
    //                "lawyerLetterDraftTimes": 1,
    //                "legalInstrumentsReviewTimes": 1,
    //                "legalLectureTimes": 1,
    //                "featuredTopicLectureTimes": 1,
    //                "contractInstrumentTranslationTimes": 1,
    //                "overseasLawyerServiceConsultationTimes": 1,
    //                "markedPrice": 99.00,
    //                "discount": 0.00,
    //                "price": 99.00,
    //                "logoImg": "D:\\lvtappImg\\普通卡@3x.png",
    //                "state": "0",
    //                "createTime": null,
    //                "creator": null,
    //                "modifyTime": null,
    //                "modifier": null,
    //                "totalTime": "120",
    //                "cardType": {
    //                      "id": 2,
    //                    "name": "普通卡"
    //                }
//    },
    //            "userId": "18217246046",
    //            "amount": 99.00,
    //            "cardNo": "y18217246046",
    //            "createTime": 1503385820000,
    //            "creator": "y18217246046",
    //            "cardState": "-1",
    //            "totalTime": "120",
    //            "invoiceState": "0",
    //            "leftTime": "120",
    //            "invoiceRise": null,
    //            "company": null,
    //            "registrationNumber": "7",
    //            "onlineLegalAdviceTime": 8,
    //            "seniorCounselAdviceTimes": 2,
    //            "legalInstrumentsDraftTimes": 4,
    //            "lawyerLetterDraftTimes": 3,
    //            "legalInstrumentsReviewTimes": 3,
    //            "legalLectureTimes": 1,
    //            "featuredTopicLectureTimes": 2,
    //            "contractInstrumentTranslationTimes": 1,
    //            "overseasLawyerServiceConsultationTimes": 2,
    //            "cardType": {
    //                 "id": 2,
    //                "name": "普通卡"
    //                       },
    //            "cardStateStr": null,
    //            "address": {
    //                "id": 55,
    //                "cardNo": "y18217246046",
    //                "province": "上海",
    //                "city": "上海",
    //                "district": "静安区",
    //                "street": "西城镇",
    //                "detailAddress": "江场三路181号",
    //                "recipients": "张老板",
    //                "phoneNum": "18217246046",
    //                "isDefault": 0,
    //                "status": -1
    //                  },
    //            "filingTime": null
    private String id;
    private String orderNo;
    private String tradeTime;
    private String activeStartTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public ServiceCardResponse getCard() {
        return card;
    }

    public void setCard(ServiceCardResponse card) {
        this.card = card;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String activeEndTime;
    private ServiceCardResponse card;
    private String userId;
    private String amount;
    private String cardNo;
}
