/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 基本异常处理类
 * <p>
 * 显示层通过该异常，显示逻辑层错误或异常。 逻辑层需要显示的异常和错误，必须继承该类。
 * 
 * @author 王伟
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 8118140909982575423L;

	private static final Log log = LogFactory.getLog(BaseException.class);

	private String pageMessage;

	/**
	 * 构造详细消息为null新异常
	 * 
	 *@param cause
	 *            传入异常
	 */
	public BaseException() {
		this(null, null);
	}

	/**
	 * 构造指定详细消息的新异常
	 * <p>
	 * 一般处理逻辑异常信息
	 * 
	 * @param message
	 *            异常（错误）消息
	 * @param pageMessage
	 *            页面显示异常（错误）消息
	 */
	public BaseException(String message, String pageMessage) {
		this(message, pageMessage, null, null);
	}

	/**
	 *构造指定详细消息的新异常
	 * <p>
	 * 一般处理逻辑异常信息,pageMessage可以注入参数
	 * 
	 * @see java.text.MessageFormat
	 * 
	 * @param message
	 *            异常（错误）消息
	 * @param pageMessage
	 *            页面显示异常（错误）消息
	 * @param parameters
	 *            页面异常消息参数
	 */
	public BaseException(String message, String pageMessage, Object[] parameters) {
		this(message, pageMessage, parameters, null);
	}

	/**
	 * 构造带指定详细消息和原因的新异常
	 * 
	 * @param message
	 *            异常（错误）消息
	 * @param pageMessage
	 *            页面显示异常（错误）消息
	 * @param cause
	 *            传入异常
	 */
	public BaseException(String message, String pageMessage, Throwable cause) {
		this(message, pageMessage, null, cause);
	}

	/**
	 * 构造带指定详细消息和原因的新异常
	 * 
	 * <p>
	 * pageMessage可以注入参数
	 * </p>
	 * 
	 * @param message
	 *            异常（错误）消息
	 * @param pageMessage
	 *            页面显示异常（错误）消息
	 * @param parameters
	 *            页面异常消息参数
	 * @param cause
	 *            传入异常
	 */
	public BaseException(String message, String pageMessage,
			Object[] parameters, Throwable cause) {
		super(message, cause);
		if (cause != null) {
			log.error(cause.toString());
		}
		if (parameters == null) {
			this.pageMessage = pageMessage;
		} else {
			this.pageMessage = MessageFormat.format(pageMessage, parameters);
		}
	}

	/**
	 * 构造带指定详细消息和原因的新异常
	 * 
	 * @param cause
	 */
	public BaseException(Throwable cause) {
		this(null, null, cause);
	}

	/**
	 * 显示层得到逻辑异常（错误）信息并显示
	 * 
	 * @return 异常（错误）信息
	 */
	public String getPageMessage() {
		return (this.pageMessage == null ? "" : this.pageMessage);
	}
}
