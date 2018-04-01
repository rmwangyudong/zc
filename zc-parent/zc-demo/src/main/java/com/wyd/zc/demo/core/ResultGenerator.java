package com.wyd.zc.demo.core;

/**
 * 响应结果生成工具
 * 
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public class ResultGenerator {

	private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";

	public static Result genSuccessResult() {
		Result result = new Result();
		return result.setHead(result.new Head().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE));
	}

	public static Result genSuccessResult(Object data) {
		Result result = new Result();
		return result.setHead(result.new Head().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE)).setBody(data);
	}

	public static Result genFailResult(String message) {
		Result result = new Result();
		return result.setHead(result.new Head().setCode(ResultCode.FAIL).setMessage(message));
	}

}