package ru.javawebinar.topjava.util.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class ErrorInfo {
    public final String url;
    public final String cause;
    public final String detail;

    public ErrorInfo(CharSequence url, BindException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        this.url = url.toString();
        this.cause = e.getClass().getSimpleName();
        this.detail = sb.toString();
    }

    public ErrorInfo(CharSequence url, Throwable ex) {
        this.url = url.toString();
        this.cause = ex.getClass().getSimpleName();
        this.detail = ex.getLocalizedMessage();
    }
}
