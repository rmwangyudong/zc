package com.wyd.zc.demo.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * 
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public enum ResultCode {

	/**
	 * 成功
	 */
	SUCCESS("200"),
	/**
	 * 失败
	 */
	FAIL("400"),
	/**
	 * 未认证（签名错误）
	 */
	UNAUTHORIZED("401"),
	/**
	 * 安全阻拦
	 */
	FORBIDDEN("403"),
	/**
	 * 接口不存在
	 */
	NOT_FOUND("404"),
	/**
	 * 服务器内部错误
	 */
	INTERNAL_SERVER_ERROR("500");

	public String code;

	ResultCode(String code) {
		this.code = code;
	}

}
