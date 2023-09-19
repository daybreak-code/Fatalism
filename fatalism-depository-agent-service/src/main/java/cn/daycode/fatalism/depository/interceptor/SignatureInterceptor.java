package cn.daycode.fatalism.depository.interceptor;

import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.depository.service.ConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Service
public class SignatureInterceptor implements Interceptor{

    private final String METHOD_GET = "GET";

    @Autowired
    private ConfigService configService;


    private final String INPUT_CHARSET = "UTF-8";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        Request requestProcess = null;
        if (METHOD_GET.equals(request.method())) {
            String url = request.url().toString();
            final String reqData = getParam(url, "reqData");
            String jsonString = EncryptUtil.decodeUTF8StringBase64(reqData);
            final String serviceName = getParam(url, "serviceName");
            final String platformNo = getParam(url, "platformNo");
            String base = url.substring(0, url.indexOf("?") + 1);

            final String sign = RSAUtil.sign(jsonString, configService.getP2pPrivateKey(), INPUT_CHARSET);
            url = base + "serviceName=" + URLEncoder.encode(serviceName, INPUT_CHARSET)
                    + "&platformNo=" + URLEncoder.encode(platformNo, INPUT_CHARSET)
                    + "&reqData=" + URLEncoder.encode(reqData, INPUT_CHARSET)
                    + "&signature=" + URLEncoder.encode(sign, INPUT_CHARSET);
            Request.Builder builder = request.newBuilder();
            builder.get().url(url);
            requestProcess = builder.build();
            response = chain.proceed(requestProcess);
            final String result = response.body().string();
            final JSONObject parseObject = JSON.parseObject(result);
            final String respData = parseObject.getString("respData");
            final String signature = parseObject.getString("signature");
            if (RSAUtil.verify(respData, signature, configService.getDepositoryPublicKey(), INPUT_CHARSET)) {
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), parseObject.toJSONString())).build();
            } else {
                parseObject.put("signature", "false");
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), parseObject.toJSONString())).build();
            }
        } else {
            response = chain.proceed(request);
        }
        return response;
    }


    public static String getParam(String url, String name) {
        String params = url.substring(url.indexOf("?") + 1, url.length());
        final String[] splitter = params.split("&");
        for (String str : splitter) {
            if (str.startsWith(name + "=")) {
                return str.substring(name.length() + 1);
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String str = "http://sdfasdf?serviceName=wanxinp2p&platformNo=wanxinp2p&reqData=eyJhbW91bnQiOjExMTEsImJpelR5cGUiOiJURU5ERVIiLCJwcm9qZWN0Tm8iOiJQUk9fREQxMzAyRTQxQjA1NDZCMTk2Mjg2RkY2MTQ2Q0JCOUYiLCJyZXF1ZXN0Tm8iOiJSRVFfMjJDMjZDQzY0RkIyNEEyRjlCNjU2OUQ2MzgzQkEwMUIiLCJ1c2VyTm8iOiI0In0=";
        String base = str.substring(0, str.indexOf("?") + 1);
        System.out.println(base);
        System.out.println(getParam(str, "reqData"));
    }

}
