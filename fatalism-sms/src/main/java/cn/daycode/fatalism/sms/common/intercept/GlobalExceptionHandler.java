package cn.daycode.fatalism.sms.common.intercept;


import cn.daycode.fatalism.sms.common.domain.BusinessException;
import cn.daycode.fatalism.sms.common.domain.CommonErrorCode;
import cn.daycode.fatalism.sms.common.domain.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;



@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public RestResponse<Nullable> exceptionGet(HttpServletRequest req, HttpServletResponse response , Exception e) {
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			if(CommonErrorCode.CUSTOM.equals(be.getErrorCode())){
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getMessage());
			}else{
				return new RestResponse<Nullable>(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
			}

		}else if(e instanceof NoHandlerFoundException){
			return new RestResponse<Nullable>(404, "找不到资源");
		}else if(e instanceof HttpRequestMethodNotSupportedException){
			return new RestResponse<Nullable>(405, "method 方法不支持");
		}else if(e instanceof HttpMediaTypeNotSupportedException){
			return new RestResponse<Nullable>(415, "不支持媒体类型");
		}

		LOGGER.error("【系统异常】{}", e);
		return  new RestResponse<Nullable>(CommonErrorCode.UNKNOWN.getCode(), CommonErrorCode.UNKNOWN.getDesc());
	}

}
