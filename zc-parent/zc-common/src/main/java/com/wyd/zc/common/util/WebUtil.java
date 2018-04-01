package com.wyd.zc.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wyd.zc.common.constant.ZcConstant;

public final class WebUtil {

	public final static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public final static HttpSession getSession() {
		return getRequest().getSession();
	}

	public final static Object getRequestAttribute(String name) {
		return getRequest().getAttribute(name);
	}

	public final static <T> T getRequestAttribute(String name, Class<T> valueType) {
		return valueType.cast(getRequestAttribute(name));
	}

	public final static Object getSessionAttribute(String name) {
		return getSession().getAttribute(name);
	}

	public final static <T> T getSessionAttribute(String name, Class<T> valueType) {
		return valueType.cast(getSessionAttribute(name));
	}

	public final static User getCurrentUser() {
		return getSessionAttribute(ZcConstant.ZC_CURRENT_USER, User.class);
	}

	public final static String getCurrentUserId() {
		return getSessionAttribute(ZcConstant.ZC_CURRENT_USER_ID, String.class);
	}

	public final static void setRequestAttribute(String name, Object obj) {
		getRequest().setAttribute(name, obj);
	}

	public final static void setSessionAttribute(String name, Object obj) {
		getSession().setAttribute(name, obj);
	}

}
