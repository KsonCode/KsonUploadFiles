package com.example.ksonuploadfiles;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.ksonuploadfiles.api.UserApi;
import com.example.ksonuploadfiles.bean.UploadInfo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            File file = new File(path);
            //如果文件存在
            if (file!=null&&file.exists()){

                //图片请求体
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
                //对图片请求体对象，封装成multipart对象，文件表单对象
                MultipartBody.Part filePart = MultipartBody.Part.create(requestBody);

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
}
