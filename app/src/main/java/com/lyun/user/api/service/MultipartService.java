package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Gordon
 * @since 2017/9/4
 * do()
 */

public interface MultipartService {
    /**
     * 上传图片
     */
    @Multipart
    @POST(APIConstants.UPDATE_HEADER)
    Observable<APIResult> uploadImages(@Part MultipartBody.Part cardNo, @Part MultipartBody.Part userImage);
}
