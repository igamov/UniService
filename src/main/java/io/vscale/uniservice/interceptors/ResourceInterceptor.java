package io.vscale.uniservice.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class ResourceInterceptor implements HandlerInterceptor{

    private final Long deployTime;

    public ResourceInterceptor(Long deployTime) {
        this.deployTime = deployTime;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o){
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView){

        if(modelAndView != null){

            String deployTimeAsString = String.valueOf(this.deployTime);
            modelAndView.addObject("v", deployTimeAsString);

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
