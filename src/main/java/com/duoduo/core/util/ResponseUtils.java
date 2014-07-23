package com.duoduo.core.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 直接输出文本到界面的帮助类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:43:25
 * @version 1.0.0
 */
public class ResponseUtils {

	// header 常量定义
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	private static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	// 绕过jsp/freemaker直接输出文本的函数 //

	/**
	 * 直接输出内容的简便函数. eg.<br />
	 * render("text/plain", "hello", "encoding:GBK");<br />
	 * render("text/plain", "hello", "no-cache:false");<br />
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final HttpServletResponse response, final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			response.setCharacterEncoding(encoding);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.addHeader("Cache-Control", "no-store");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 */
	public static void renderText(final HttpServletResponse response, final String text, final String... headers) {
		render(response, "text/plain", text, headers);
	}

	/**
	 * 直接输出HTML.
	 */
	public static void renderHtml(final HttpServletResponse response, final String html, final String... headers) {
		render(response, "text/html", html, headers);
	}

	/**
	 * 直接输出XML.
	 */
	public static void renderXml(final HttpServletResponse response, final String xml, final String... headers) {
		render(response, "text/xml", xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * @param string json字符串.
	 */
	public static void renderJson(final HttpServletResponse response, final String string, final String... headers) {
		renderText(response, string, headers);
	}

	/**
	 * 直接输出JSON.
	 * @param map Map对象,将被转化为json字符串.
	 */
	public static void renderJson(final HttpServletResponse response, final Map<?, ?> map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		renderJson(response, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * @param object Java对象,将被转化为json字符串.
	 */
	public static void renderJson(final HttpServletResponse response, final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		renderJson(response, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * @param collection 集合对象,将被转化为json字符串.
	 */
	public static void renderJson(final HttpServletResponse response, final Collection<?> collection,
			final String... headers) {
		String jsonString = JSONArray.fromObject(collection).toString();
		renderJson(response, jsonString, headers);
	}

}
