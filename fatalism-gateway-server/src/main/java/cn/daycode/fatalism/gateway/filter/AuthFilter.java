package cn.daycode.fatalism.gateway.filter;

import cn.daycode.fatalism.common.util.EncryptUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre"; //前置过滤器，可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run() {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication==null || !(authentication instanceof OAuth2Authentication)){
            return null;
        }
        OAuth2Authentication oauth2Authentication=(OAuth2Authentication)authentication;

        Map<String,String> jsonToken = new HashMap<>
                (oauth2Authentication.getOAuth2Request().getRequestParameters());

        RequestContext ctx = RequestContext.getCurrentContext();
        javax.servlet.http.HttpServletRequest request = ctx.getRequest();
        request.getParameterMap();
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>();
        }
        List<String> arrayList = new ArrayList<>();
        arrayList.add(EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));
        requestQueryParams.put("jsonToken", arrayList);
        ctx.setRequestQueryParams(requestQueryParams);
        return null;
    }
}
