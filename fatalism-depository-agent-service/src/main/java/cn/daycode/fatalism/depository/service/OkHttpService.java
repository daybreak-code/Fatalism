package cn.daycode.fatalism.depository.service;

import cn.daycode.fatalism.depository.interceptor.SignatureInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OkHttpService {

    @Autowired
    private SignatureInterceptor signatureInterceptor;


    public String doSyncGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(signatureInterceptor).build();
        Request request = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            log.warn("Request got exception: ", e);
        }
        return "";
    }
}
