package com.embed.skin.api;


import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.model.respone.LoginRespone;
import com.embed.skin.model.respone.UploadFileRespone;
import java.util.List;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * 请求的相关接口
 */
public interface ApiService {

    @Multipart
    @POST("/v1/skintype/detect")
    Observable<BaseRespone<UploadFileRespone>> upLoadsImg(@Url String url, @Part List<MultipartBody.Part> partList);
    /**
     *  用户登录接口
     * @param data
     * @return
     */
    @POST("login")
    Observable<BaseRespone<LoginRespone>> login(@Body String data);


    /**
     *  用户登录接口
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<BaseRespone> updateImage(@Field("image") String image);


    @Multipart
    @POST("/v1/skintype/detect")
    Observable<BaseRespone<UploadFileRespone>> upLoadHead(@Url String url, @Part List<MultipartBody.Part> partList);

}
