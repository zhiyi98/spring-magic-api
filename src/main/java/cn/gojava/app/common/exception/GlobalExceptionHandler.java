package cn.gojava.app.common.exception;

import cn.gojava.app.common.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return HttpResult.error("请求方式不支持，请联系管理员！");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public HttpResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return Objects.nonNull(code) ? HttpResult.error(code, e.getMessage()) : HttpResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public HttpResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return HttpResult.error("系统运行异常，请重试！");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public HttpResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return HttpResult.error("系统异常，请重试！");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public HttpResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return HttpResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return HttpResult.error(message);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public HttpResult handleNullPointerException(NullPointerException e)
    {
        log.error(e.getMessage(), e);
        return HttpResult.error(e.getMessage());
    }

}
