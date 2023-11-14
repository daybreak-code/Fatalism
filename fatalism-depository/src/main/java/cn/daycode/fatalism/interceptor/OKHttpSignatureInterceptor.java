package cn.daycode.fatalism.interceptor;

import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Service
public class OKHttpSignatureInterceptor implements Interceptor {
    private final String METHOD_GET = "GET";

    @Autowired
    private ConfigService configService;


    private final String INPUT_CHARSET = "UTF-8";

    @Override
    public Response intercept(Chain chain) throws IOException {
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

            final String sign = RSAUtil.sign(jsonString, "configService.getDepositoryPrivateKey()", INPUT_CHARSET);
            url = base + "serviceName=" + URLEncoder.encode(serviceName, INPUT_CHARSET) + "&platformNo=" + URLEncoder
                    .encode(platformNo, INPUT_CHARSET) + "&reqData=" + URLEncoder.encode(reqData, INPUT_CHARSET)
                    + "&signature=" + URLEncoder.encode(sign, INPUT_CHARSET);
            Request.Builder builder = request.newBuilder();
            builder.get().url(url);
            requestProcess = builder.build();
            response = chain.proceed(requestProcess);
        } else {
            response = chain.proceed(request);
        }
        return response;
    }


    public String getParam(String url, String name) {
        String params = url.substring(url.indexOf("?") + 1, url.length());
        final String[] splitter = params.split("&");
        for (String str : splitter) {
            if (str.startsWith(name + "=")) {
                return str.substring(name.length() + 1);
            }
        }
        return "";
    }
}
