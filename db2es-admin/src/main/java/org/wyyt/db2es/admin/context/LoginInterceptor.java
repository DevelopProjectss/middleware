package org.wyyt.db2es.admin.context;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wyyt.db2es.admin.common.Constants;
import org.wyyt.db2es.admin.entity.vo.AdminVo;
import org.wyyt.tool.exception.ExceptionTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interceptor for login
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize         10/1/2020      Initialize   *
 * *****************************************************************
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            try {
                final AdminVo adminVo = (AdminVo) SecurityUtils.getSubject().getPrincipal();
                if (null != adminVo) {
                    request.setAttribute(Constants.CURRENT_ADMIN_LOGIN, adminVo);
                }
            } catch (Exception e) {
                log.error(ExceptionTool.getRootCauseMessage(e), e);
            }
        }
        return super.preHandle(request, response, handler);
    }
}