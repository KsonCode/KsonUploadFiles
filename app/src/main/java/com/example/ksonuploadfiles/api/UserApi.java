package com.example.ksonuploadfiles.api;

import com.example.ksonuploadfiles.bean.UploadInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserApi {
    @POST("small/user/verify/v1/modifyHeadPic")
    @Multipart
    Observable<UploadInfo> upload(@HeaderMap Map<String,String> headerParams, @Part MultipartBody.Part f);

    @POST("small/user/verify/v1/modifyHeadPic")
    @Multipart
    Observable<UploadInfo> upload2(@HeaderMap Map<String,String> headerParams,@Part MultipartBody.Part nickname, @Part MultipartBody.Part f);


    /**
     * 发布圈子
     * @param headerMap
     * @param images
     * @return
     */
    @POST
    @Multipart
    Observable<UploadInfo> putMessage(@HeaderMap Map<String,String> headerMap, @PartMap Map<String,RequestBody> params, @Part List<MultipartBody.Part> images);

    /**
     * 发布圈子
     * @param headerMap
     * @param images
     * @return
     */
    @POST
    @Multipart
    Observable<UploadInfo> putMessage3(@HeaderMap Map<String,String> headerMap, @PartMap List<MultipartBody.Part> params, @Part List<MultipartBody.Part> images);

    /**
     * 发布圈子
     * @param headerMap
     * @return
     */
    @POST
    @Multipart
    Observable<UploadInfo> putMessage2(@HeaderMap Map<String,String> headerMap, @PartMap Map<String,RequestBody> params);


}
