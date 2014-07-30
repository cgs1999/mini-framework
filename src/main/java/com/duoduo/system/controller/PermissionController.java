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
import com.duoduo.system.service.PermissionService;
import com.duoduo.system.service.ResourceService;
import com.duoduo.system.vo.PermissionVO;
import com.duoduo.system.vo.ResourceVO;

/**
 * 权限Controller
 * @author chengesheng
 * @date 2014-3-19 下午6:13:00
 */
@Controller
@RequestMapping("/system/permission")
public class PermissionController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private PermissionService PermissionService;
	@Resource
	private ResourceService resourceService;

	private String listPage = "permission/permission-list";
	private String formPage = "permission/permission-form";

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
		PermissionVO permissionVO = PermissionService.getById(id);

		// 设置菜单信息
		String resourceIds = "";
		String resourceNames = "";
		List<ResourceVO> menus = resourceService.listByPermissionId("" + permissionVO.getId());
		for (ResourceVO menu : menus) {
			resourceIds += "," + menu.getId();
			resourceNames += "," + menu.getName();
		}
		// 处理前面多余的","
		if (!"".equals(resourceIds)) {
			permissionVO.setResourceIds(resourceIds.substring(1));
			permissionVO.setResourceNames(resourceNames.substring(1));
		}

		model.addAttribute("data", permissionVO);

		return formPage;
	}

	@RequestMapping(value = "/getPageList", method = RequestMethod.POST)
	public void getPageList(HttpServletResponse response, Page<PermissionVO> page,
			@RequestParam(value = "name", required = false) String name) {
		page = PermissionService.pagingList(name, page);
		ResponseUtils.renderJson(response, page);
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public void listAll(HttpServletResponse response) {
		List<PermissionVO> permissions = PermissionService.listAll();
		ResponseUtils.renderJson(response, permissions);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, PermissionVO permissionVO) {
		ResponseUtils.renderJson(response, save(permissionVO));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, String id) {
		ResponseUtils.renderJson(response, delete(id));
	}

	private Message<String> save(PermissionVO permissionVO) {
		Message<String> message = new Message<String>(true, "保存成功");

		try {
			int nResult = -1;
			if (permissionVO != null) {
				// 校验角色名称
				PermissionVO vo = PermissionService.getByName(permissionVO.getName());
				if (vo != null && !permissionVO.equals(vo)) {
					message.setSuccess(false);
					message.setDescription("角色名称已存在");
					return message;
				}

				if (permissionVO.getId() == null) {
					PermissionService.create(permissionVO);
				} else {
					PermissionService.update(permissionVO);
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
			boolean ret = PermissionService.delete(id);
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
