package com.example.ksonuploadfiles.api;

import com.example.ksonuploadfiles.bean.UploadInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
    @POST("small/user/verify/v1/modifyHeadPic")
    @Multipart
    Observable<UploadInfo> upload(@HeaderMap Map<String,String> headerParams, @Part MultipartBody.Part f);
}
