package org.wyyt.db2es.admin.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wyyt.tool.exception.ExceptionTool;
import org.wyyt.tool.web.Result;
import org.wyyt.tool.web.ResultCode;

import javax.servlet.http.HttpServletRequest;

/**
 * As the base class of the interface controller. Provide common functions such as unified error handling.
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize        10/1/2020        Initialize  *
 * *****************************************************************
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handleOtherException(final Model model,
                                       final HttpServletRequest request,
                                       final Exception e) {
        return handleException(model, request, e);
    }

    private Object handleException(final Model model,
                                   final HttpServletRequest request,
                                   final Exception exception) {
        String errorMsg = ExceptionTool.getRootCauseMessage(exception);
        log.error(errorMsg, exception);
        if (isAjax(request)) {
            ResultCode respondCode = ResultCode.get(errorMsg);
            if (null == respondCode) {
                return Result.error(errorMsg);
            } else {
                return Result.create(respondCode);
            }
        } else {
            model.addAttribute("error", getStackTrace(exception));
            return new ModelAndView("common/500");
        }
    }

    private boolean isAjax(final HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    private String getStackTrace(final Exception exception) {
        return ExceptionTool.getStackTraceInHtml(exception);
    }
}