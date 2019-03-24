package cn.mrerror.one.exception;

import cn.mrerror.one.configuration.Applications;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常类处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Object exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView("exception");
        Object result =mv;
        if(ex instanceof PlatformException){
            if(((PlatformException) ex).platformType == Applications.PLATFORM_API){
                Map<String,Object>  exMap = new HashMap<String,Object>();
                                    exMap.put("code","501");
                                    exMap.put("desc","空指针异常");
                result= exMap;
            }
        }
        mv.addObject("exception", ex);
        return result;
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public Object nullExceptionHandler(Exception ex){
        return exceptionResult(Applications.EXCEPTION_501_CODE,"空指针异常");
    }

    @ExceptionHandler({TokenRepeatCommitException.class})
    @ResponseBody
    public Object repeatExceptionHandler(Exception ex){
        return exceptionResult(Applications.EXCEPTION_501_CODE,ex.getMessage());
    }

    private Map<String,Object> exceptionResult(String code,String msg){
        Map<String,Object>  exMap = new HashMap<String,Object>();
                            exMap.put("code",code);
                            exMap.put("desc",msg);
        return exMap;
    }

}
