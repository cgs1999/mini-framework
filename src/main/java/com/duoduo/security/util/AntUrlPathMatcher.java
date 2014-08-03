package com.duoduo.security.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 来自Spring framework 3.0.5 org.springframework.security.web.util.AntUrlPathMatcher
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午2:17:41
 * @version 1.0.0
 */
/**
 * Ant path strategy for URL matching.
 * @author Luke Taylor
 */
public class AntUrlPathMatcher implements UrlMatcher {

	private boolean requiresLowerCaseUrl = true;
	private PathMatcher pathMatcher = new AntPathMatcher();

	public AntUrlPathMatcher() {
		this(true);
	}

	public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = requiresLowerCaseUrl;
	}

	public Object compile(String path) {
		if (requiresLowerCaseUrl) {
			return path.toLowerCase();
		}

		return path;
	}

	public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = requiresLowerCaseUrl;
	}

	public boolean pathMatchesUrl(Object path, String url) {
		return pathMatcher.match((String) path, url);
	}

	public String getUniversalMatchPattern() {
		return "/**";
	}

	public boolean requiresLowerCaseUrl() {
		return requiresLowerCaseUrl;
	}

	public String toString() {
		return getClass().getName() + "[requiresLowerCase='" + requiresLowerCaseUrl + "']";
	}
}
