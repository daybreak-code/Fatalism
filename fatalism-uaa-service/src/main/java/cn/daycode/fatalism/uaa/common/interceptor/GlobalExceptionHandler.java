package cn.daycode.fatalism.uaa.common.interceptor;

import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.CommonErrorCode;
import cn.daycode.fatalism.common.domain.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse<Nullable> exceptionGet(HttpServletRequest req, HttpServletResponse response, Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            if (CommonErrorCode.CUSTOM.equals(be.getErrorCode())) {
                return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getMessage());
            } else {
                return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
            }

        } else if (e instanceof NoHandlerFoundException) {
            return new RestResponse<Nullable>(404, "Not found resource");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return new RestResponse<Nullable>(405, "method not support");
        } else if (e instanceof AccessDeniedException) {
            return new RestResponse<Nullable>(304, "don't have access");
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            return new RestResponse<Nullable>(415, "don't support media type");
        }

        LOGGER.error("【System Exception】{}", e);
        return new RestResponse<Nullable>(CommonErrorCode.UNKOWN.getCode(), CommonErrorCode.UNKOWN.getDesc());
    }
}
