package com.shl.controller;

import com.shl.util.DataProcesser;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    DataProcesser dp = DataProcesser.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        System.out.println("Pre Call is happening for request: " + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model)throws Exception {
        if (request.getRequestURI().contains("InvolvedPartyAuthorisationService")){
            System.out.println("Request Url : " + request.getRequestURL().toString());
            System.out.println("=========== Response Header print start ==========");

            Collection<String> headerMap = response.getHeaderNames();
            for(String key : headerMap){
                System.out.println(key + " - " + request.getHeader(key));
            }
            System.out.println("=========== Response Header print end ==========");

            System.out.println("Response Status : " + response.getStatus());
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                Object object, Exception arg3) throws Exception {

    }
}
