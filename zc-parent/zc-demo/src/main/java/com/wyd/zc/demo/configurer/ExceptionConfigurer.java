package com.wyd.zc.demo.configurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.wyd.zc.demo.core.Result;
import com.wyd.zc.demo.core.ResultCode;
import com.wyd.zc.demo.core.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionConfigurer {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		Result result = new Result();
		// 业务失败的异常，如“账号或密码错误”
		String message = e.getMessage();
		if (e instanceof ServiceException) {
			result.setHead(result.new Head().setCode(ResultCode.FAIL).setMessage(e.getMessage()));
		} else if (e instanceof NoHandlerFoundException) {
			result.setHead(result.new Head().setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在"));
		} else if (e instanceof ServletException) {
			result.setHead(result.new Head().setCode(ResultCode.FAIL).setMessage(e.getMessage()));
		} else {
			result.setHead(result.new Head().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员"));
		}
		log.error(message, e);
		return result;
	}

}
