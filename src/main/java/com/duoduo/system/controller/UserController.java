package com.duoduo.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.duoduo.core.util.ResponseUtils;
import com.duoduo.core.vo.Message;
import com.duoduo.core.vo.Page;
import com.duoduo.system.manager.RoleManager;
import com.duoduo.system.manager.UserManager;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 用户管理Controller
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午2:30:09
 * @version 1.0.0
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

	@Resource
	private UserManager userService;
	@Resource
	private RoleManager roleService;

	private String listPage = "user/user-list";
	private String formPage = "user/user-form";

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		return listPage;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ModelMap model) {
		return formPage;
	}

	@RequestMapping(value = "/{moid}", method = RequestMethod.GET)
	public String form(ModelMap model, @PathVariable String moid) {
		if (StringUtils.hasText(moid)) {
			load(model, moid);
		}
		return formPage;
	}

	@RequestMapping(value = "/getPageList", method = RequestMethod.POST)
	public void getPageList(HttpServletResponse response, Page<UserVO> page,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone) {
		page = userService.pagingList(account, name, email, phone, page);
		ResponseUtils.renderJson(response, page);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, UserVO userVO) {
		ResponseUtils.renderJson(response, save(userVO));
	}

	private void load(ModelMap model, String id) {
		UserVO userVO = userService.getById(id);

		// 设置角色信息
		String roleIds = "";
		String roleNames = "";
		List<RoleVO> roles = roleService.listByUserId(id);
		for (RoleVO role : roles) {
			roleIds += "," + role.getId();
			roleNames += "," + role.getName();
		}
		// 处理前面多余的","
		if (!"".equals(roleIds)) {
			userVO.setRoleIds(roleIds.substring(1));
			userVO.setRoleNames(roleNames.substring(1));
		}

		model.addAttribute("data", userVO);
	}

	private Message<String> save(UserVO userVO) {
		Message<String> message = new Message<String>(true, "保存成功");

		int nResult = -1;
		if (userVO != null) {
			if (userVO.getId() != null && userVO.getId().longValue() != 0) {
				userVO = userService.create(userVO);
				nResult = 1;
			} else {
				userService.update(userVO);
				nResult = 2;
			}
		}

		if (nResult < 0) {
			message.setSuccess(false);
			message.setDescription("保存失败，请联系管理员!");
		} else {
			message.setData(Integer.toString(nResult));
		}

		return message;
	}
}
