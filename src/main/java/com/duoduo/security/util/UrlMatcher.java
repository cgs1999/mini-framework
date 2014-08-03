package com.duoduo.security.util;

/**
 * 来自Spring framework 3.0.5 org.springframework.security.web.util.UrlMatcher
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午2:14:36
 * @version 1.0.0
 */
/**
 * Strategy for deciding whether configured path matches a submitted candidate URL.
 * @author Luke Taylor
 * @since 2.0
 */
public interface UrlMatcher {

	Object compile(String urlPattern);

	boolean pathMatchesUrl(Object compiledUrlPattern, String url);

	/** Returns the path which matches every URL */
	String getUniversalMatchPattern();

	/**
	 * Returns true if the matcher expects the URL to be converted to lower case before calling
	 * {@link #pathMatchesUrl(Object, String)}.
	 */
	boolean requiresLowerCaseUrl();
}
