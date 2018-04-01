package com.wyd.zc.demo.configurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import com.wyd.zc.common.util.InternetProtocol;
import com.wyd.zc.common.util.UUIDGenerator;
import com.wyd.zc.common.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MdcInterceptor());
	}

	public class MdcInterceptor implements HandlerInterceptor {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			MDC.put("deviceID", InternetProtocol.getRemoteAddr(request));
			MDC.put("userID", WebUtil.getCurrentUserId());
			MDC.put("sessionID", WebUtil.getSession().getId());
			MDC.put("bizSN", UUIDGenerator.generate());
			MDC.put("bizID", request.getServletPath() + ":" + request.getMethod());

			if (StringUtils.equalsIgnoreCase("GET", request.getMethod())) {
				log.info("请求报文：{}", request.getQueryString());
			} else {
				ContentCachingRequestWrapper requestReplacedWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
				if (null != requestReplacedWrapper) {
					log.info("请求报文：{}", IOUtils.toString(requestReplacedWrapper.getContentAsByteArray(), requestReplacedWrapper.getCharacterEncoding()));
				}
			}
			// 只有返回true才会继续向下执行，返回false取消当前请求
			return true;
		}

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		}

		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
			ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
			if (null != responseWrapper) {
				log.info("响应报文：{}", IOUtils.toString(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding()));
			}
			MDC.remove("deviceID");
			MDC.remove("userID");
			MDC.remove("sessionID");
			MDC.remove("bizSN");
			MDC.remove("bizID");
		}
	}

}
