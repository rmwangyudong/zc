package com.wyd.zc.demo.core;

/**
 * 统一API响应结果封装
 * 
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public class Result {

	private Head head;
	private Object body;

	public Head getHead() {
		return head;
	}

	public Result setHead(Head head) {
		this.head = head;
		return this;
	}

	public Object getBody() {
		return body;
	}

	public Result setBody(Object body) {
		this.body = body;
		return this;
	}

	public class Head {
		private String code;
		private String message;

		public Head setCode(ResultCode resultCode) {
			this.code = resultCode.code;
			return this;
		}

		public String getCode() {
			return code;
		}

		public Head setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getMessage() {
			return message;
		}

	}

}