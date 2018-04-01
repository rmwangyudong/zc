package com.wyd.zc.demo.core;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录
 * 
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
