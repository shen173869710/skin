package com.embed.skin.api;



import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.model.respone.UploadFileRespone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by czl on 2017/8/29.
 */

public class HttpUpLoadFile {
    public static void upLoadImage(ArrayList<String> mPhotoList) {

        String url = "";
        MultipartBody.Builder builder =new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i=0;i<mPhotoList.size();i++){
            File file = new File(mPhotoList.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
            builder.addFormDataPart("upfile[]",file.getName(),requestBody);
        }
        List<MultipartBody.Part> parts= builder.build().parts();
        DisposableObserver disposableObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                BaseRespone respone =  (BaseRespone) o;
                if (respone != null && respone.isOk()) {
                    UploadFileRespone uploadFileRespones = (UploadFileRespone) respone.getResult();
                    if(mListener != null){
                        mListener.upLoadFileSuccess(uploadFileRespones.images);
                    }
                }else {
                    onError(new Throwable());
                }

            }
            @Override
            public void onError(Throwable e) {
               if(mListener != null){
                   mListener.upLoadFileError(e);
                }
            }
            @Override
            public void onComplete() {

            }
        };
        ApiUtil.createApiService().upLoadsImg(url,parts)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);
    }

    private static UpLoadFileListener mListener=null;
    public  interface UpLoadFileListener{
        void upLoadFileSuccess(ArrayList<String> pathList);
        void upLoadFileError(Throwable e);
    }
    public static void setUpLoadFileListener(UpLoadFileListener listener){
        mListener=listener;
    }

}
