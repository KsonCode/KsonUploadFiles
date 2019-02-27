package com.example.ksonuploadfiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.ksonuploadfiles.api.UserApi;
import com.example.ksonuploadfiles.bean.UploadInfo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    /**
     * 上传头像
     * @param view
     */
    public void upload(View view) {

        //头部入参
        Map<String,String> headerParams = new HashMap<>();
        headerParams.put("userId","159");
        headerParams.put("sessionId","1551240158409159");

        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//挂载状态
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"wdmallimgs/hi.jpg";

            System.out.println("path:====="+path);
            //takephoto，选择器，选择图片，只能选择一张，回调回来一个file对象
            File file = new File(path);
            //如果文件存在
            if (file!=null&&file.exists()){

                //图片请求体
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
                //对图片请求体对象，封装成multipart对象，文件表单对象
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image",file.getName(),requestBody);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.17.8.100/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserApi userApi = retrofit.create(UserApi.class);//动态代理模式
                userApi.upload(headerParams,filePart).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<UploadInfo>() {
                            @Override
                            public void accept(UploadInfo uploadInfo) throws Exception {
                                ToastUtils.showShort(uploadInfo.message);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });

            }else{
                ToastUtils.showShort("请选择文件");
            }
        }
    }


    /**
     * 发布圈子
     * @param view
     */
    public void pushMessage(View view) {

        //头部入参
        Map<String,String> headerParams = new HashMap<>();
        headerParams.put("userId","159");
        headerParams.put("sessionId","1551240158409159");

        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//挂载状态
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"wdmallimgs/hi.jpg";

            System.out.println("path:====="+path);
            //takephoto，选择器，选择图片，只能选择一张，回调回来一个file对象
//            List<File> files = new ArrayList<>();
//            for (File file : files) {
//                //多图的文件对象
//            }
            //============多文件上传第一种======================//
            //普通业务参数
           Map<String,RequestBody> params = new HashMap<>();
            RequestBody pidBody = RequestBody.create(MediaType.parse("text/plain"),"23");
            RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"),"我已经发布了");
            params.put("commodityId",pidBody);
            params.put("content",contentBody);
            //文件表单集合
            List<MultipartBody.Part> partList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                File file = new File(path);
                //图片请求体
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
                //对图片请求体对象，封装成multipart对象，文件表单对象
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image",file.getName(),requestBody);

                partList.add(filePart);

            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.17.8.100/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserApi userApi = retrofit.create(UserApi.class);//动态代理模式
            userApi.putMessage(headerParams,params,partList).subscribeOn(
                    Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UploadInfo>() {
                @Override
                public void accept(UploadInfo uploadInfo) throws Exception {


                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {

                }
            });


            //============多文件上传第二种======================//

            Map<String,RequestBody> params2 = new HashMap<>();
            params.put("commodityId",RequestBody.create(MediaType.parse("multipart/form-data"),"23"));
            params.put("content",RequestBody.create(MediaType.parse("multipart/form-data"),"我是发布的圈子"));

            for (int i = 0; i < 3; i++) {

                File file = new File(path);
                params2.put("image\";filename=\""+file.getName()+"",RequestBody.create(MediaType.parse("image/*"),file));
            }

            userApi.putMessage2(headerParams,params2);


        }

    }
}
