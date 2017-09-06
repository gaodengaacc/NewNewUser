package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.Account;
import com.lyun.user.api.API;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Gordon
 * @since 2017/9/4
 * do()
 */

public class MultipartModel extends Model {
    public Observable<APIResult> upHeader(String path) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        ProgressRequestBody requestBody1 = new ProgressRequestBody(requestBody, new UploadProgressListener() {
//            @Override
//            public void onProgress(long currentBytesCount, long totalBytesCount) {
//                System.out.print("Multipart" + currentBytesCount + ":::::::" + totalBytesCount);
//            }
//        });
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("userImage", file.getName(), requestBody);
        MultipartBody.Part cardPart = MultipartBody.Part.createFormData("cardNo", Account.preference().getCardNo());
        return API.multipartService.uploadImages(cardPart, imagePart).onErrorReturn(throwable -> ErrorParser.mockResult(throwable));
    }
}
