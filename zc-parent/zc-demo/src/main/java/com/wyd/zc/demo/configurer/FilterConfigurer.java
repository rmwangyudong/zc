package com.wyd.zc.demo.configurer;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Configuration
public class FilterConfigurer {

	@Bean
	public FilterRegistrationBean<ServletFilter> servletFilter() {
		FilterRegistrationBean<ServletFilter> bean = new FilterRegistrationBean<ServletFilter>();
		bean.setFilter(new ServletFilter());
		Collection<String> urlPatterns = new LinkedHashSet<String>();
		urlPatterns.add("/*");
		bean.setUrlPatterns(urlPatterns);
		bean.setOrder(1);
		return bean;
	}

	public class ServletFilter implements Filter {

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {

		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
				ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
				ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
				chain.doFilter(requestWrapper, responseWrapper);
				responseWrapper.copyBodyToResponse();
			} else {
				chain.doFilter(request, response);
			}
		}

		@Override
		public void destroy() {

		}

	}

}
