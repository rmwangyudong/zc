package com.wyd.zc.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public final class InternetProtocol {

	private static final String COMMA = ",";
	private static final String UNKNOWN = "unknown";

	/**
	 * 获取客户端IP地址.<br>
	 * 支持多级反向代理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 客户端真实IP地址
	 */
	public static String getRemoteAddr(final HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Forwarded-For");
		// 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
		if (isEffective(remoteAddr) && (remoteAddr.indexOf(COMMA) > -1)) {
			String[] array = remoteAddr.split(COMMA);
			for (String element : array) {
				if (isEffective(element)) {
					remoteAddr = element;
					break;
				}
			}
		}
		if (!isEffective(remoteAddr)) {
			remoteAddr = request.getHeader("X-Real-IP");
		}
		if (!isEffective(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		return remoteAddr;
	}

	/**
	 * 获取客户端源端口
	 * 
	 * @param request
	 * @return
	 */
	public static Long getRemotePort(final HttpServletRequest request) {
		String port = request.getHeader("remote-port");
		if (StringUtils.isNotBlank(port)) {
			return Long.parseLong(port);
		} else {
			return 0L;
		}
	}

	/**
	 * 远程地址是否有效.
	 * 
	 * @param remoteAddr
	 *            远程地址
	 * @return true代表远程地址有效，false代表远程地址无效
	 */
	private static boolean isEffective(final String remoteAddr) {
		boolean isEffective = false;
		if (StringUtils.isNotBlank(remoteAddr) && !StringUtils.equalsIgnoreCase(UNKNOWN, StringUtils.trim(remoteAddr))) {
			isEffective = true;
		}
		return isEffective;
	}

}