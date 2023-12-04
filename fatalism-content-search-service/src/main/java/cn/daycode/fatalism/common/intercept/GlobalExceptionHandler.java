package cn.daycode.fatalism.common.intercept;

import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.CommonErrorCode;
import cn.daycode.fatalism.common.domain.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse<Nullable> exceptionGet(HttpServletRequest req, HttpServletResponse response, Exception e){
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            if(CommonErrorCode.CUSTOM.equals(be.getErrorCode())){
                return new RestResponse(be.getErrorCode().getCode(), be.getMessage());
            }else{
                return new RestResponse(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
            }

        }else if(e instanceof NoHandlerFoundException){
            return new RestResponse(404, "can't found resource");
        }else if(e instanceof HttpRequestMethodNotSupportedException){
            return new RestResponse(405, "method don't support");
        }else if(e instanceof HttpMediaTypeNotSupportedException){
            return new RestResponse(415, "don't support media type");
        }

        log.error("【System Exception】" + e.getMessage());
        return  new RestResponse<>(CommonErrorCode.UNKOWN.getCode(), CommonErrorCode.UNKOWN.getDesc());
    }

}
