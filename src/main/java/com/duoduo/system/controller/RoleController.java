package com.duoduo.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.duoduo.core.util.ResponseUtils;
import com.duoduo.core.vo.Message;
import com.duoduo.core.vo.Page;
import com.duoduo.system.Constants;
import com.duoduo.system.manager.ResourceManager;
import com.duoduo.system.manager.RoleManager;
import com.duoduo.system.vo.ResourceVO;
import com.duoduo.system.vo.RoleVO;

/**
 * 角色Controller
 * @author chengesheng
 * @date 2014-3-19 下午6:13:00
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private RoleManager roleService;
	@Resource
	private ResourceManager resourceService;

	private String listPage = "role/role-list";
	private String formPage = "role/role-form";
	private String readPage = "role/role-read";
	private String selectFromAllPage = "role/selectFromAllRole";

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		return listPage;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ModelMap model) {
		return formPage;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String form(ModelMap model, @PathVariable String id) {
		RoleVO roleVO = roleService.getById(id);

		// 设置菜单信息
		String menuIds = "";
		String menuNames = "";
		List<ResourceVO> menus = resourceService.listByRoleId("" + roleVO.getId());
		for (ResourceVO menu : menus) {
			menuIds += "," + menu.getId();
			menuNames += "," + menu.getName();
		}
		// 处理前面多余的","
		if (!"".equals(menuIds)) {
			roleVO.setResourceIds(menuIds.substring(1));
			roleVO.setResourceNames(menuNames.substring(1));
		}

		model.addAttribute("data", roleVO);

		if (Constants.SYSTEM_ROLE.equals(roleVO.getType())) {
			return readPage;
		} else {
			return formPage;
		}
	}

	@RequestMapping(value = "/selectFromAllRole", method = RequestMethod.GET)
	public String selectFromAllRole(ModelMap model) {
		return selectFromAllPage;
	}

	@RequestMapping(value = "/getPageList", method = RequestMethod.POST)
	public void getPageList(HttpServletResponse response, Page<RoleVO> page,
			@RequestParam(value = "name", required = false) String name) {
		page = roleService.pagingList(name, page);
		ResponseUtils.renderJson(response, page);
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public void listAll(HttpServletResponse response) {
		List<RoleVO> roles = roleService.listAll();
		ResponseUtils.renderJson(response, roles);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, RoleVO roleVO) {
		ResponseUtils.renderJson(response, save(roleVO));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, String id) {
		ResponseUtils.renderJson(response, delete(id));
	}

	private Message<String> save(RoleVO roleVO) {
		Message<String> message = new Message<String>(true, "保存成功");

		try {
			int nResult = -1;
			if (roleVO != null) {
				// 校验角色名称
				RoleVO vo = roleService.getByName(roleVO.getName());
				if (vo != null && !roleVO.equals(vo)) {
					message.setSuccess(false);
					message.setDescription("角色名称已存在");
					return message;
				}

				if (roleVO.getId() == null) {
					roleService.create(roleVO);
				} else {
					roleService.update(roleVO);
				}
			}

			if (nResult < 0) {
				message.setSuccess(false);
				message.setDescription("保存失败，请联系管理员!");
			} else {
				message.setData(Integer.toString(nResult));
			}
		} catch (RuntimeException re) {
			message.setSuccess(false);
			message.setDescription(re.getMessage());
			re.printStackTrace();
			logger.info(re.getMessage());
		} catch (Exception e) {
			message.setSuccess(false);
			message.setDescription("操作出现未知错误，请与系统管理员联系！");
			e.printStackTrace();
			logger.info("操作失败，" + e.getMessage());
		}

		return message;
	}

	private Message<String> delete(String id) {
		Message<String> message = new Message<String>(true, "删除成功");

		try {
			boolean ret = roleService.delete(id);
			if (!ret) {
				message.setSuccess(false);
				message.setDescription("删除失败，请联系管理员!");
			}
		} catch (RuntimeException re) {
			message.setSuccess(false);
			message.setDescription(re.getMessage());
			re.printStackTrace();
			logger.info(re.getMessage());
		} catch (Exception e) {
			message.setSuccess(false);
			message.setDescription("操作出现未知错误，请与系统管理员联系！");
			e.printStackTrace();
			logger.info("操作失败，" + e.getMessage());
		}

		return message;
	}
}
