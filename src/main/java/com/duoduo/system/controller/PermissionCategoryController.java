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
import com.duoduo.system.service.PermissionCategoryService;
import com.duoduo.system.service.PermissionService;
import com.duoduo.system.vo.PermissionCategoryVO;

/**
 * 权限分类Controller
 * @author chengesheng
 * @date 2014-3-19 下午6:13:00
 */
@Controller
@RequestMapping("/system/permissionCategory")
public class PermissionCategoryController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private PermissionCategoryService permissionCategoryService;
	@Resource
	private PermissionService permissionService;

	private String listPage = "permissionCategory/permissionCategory-list";
	private String formPage = "permissionCategory/permissionCategory-form";

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
		model.addAttribute("data", permissionCategoryService.getById(id));
		return formPage;
	}

	@RequestMapping(value = "/getPageList", method = RequestMethod.POST)
	public void getPageList(HttpServletResponse response, Page<PermissionCategoryVO> page,
			@RequestParam(value = "name", required = false) String name) {
		page = permissionCategoryService.pagingList(name, page);
		ResponseUtils.renderJson(response, page);
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public void listAll(HttpServletResponse response) {
		List<PermissionCategoryVO> permissionCategorys = permissionCategoryService.listAll();
		ResponseUtils.renderJson(response, permissionCategorys);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, PermissionCategoryVO permissionCategoryVO) {
		ResponseUtils.renderJson(response, save(permissionCategoryVO));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, String id) {
		ResponseUtils.renderJson(response, delete(id));
	}

	private Message<String> save(PermissionCategoryVO permissionCategoryVO) {
		Message<String> message = new Message<String>(true, "保存成功");

		try {
			int nResult = -1;
			if (permissionCategoryVO != null) {
				// 校验权限分类名称
				PermissionCategoryVO vo = permissionCategoryService.getByName(permissionCategoryVO.getName());
				if (vo != null && !permissionCategoryVO.equals(vo)) {
					message.setSuccess(false);
					message.setDescription("权限分类名称已存在");
					return message;
				}

				if (permissionCategoryVO.getId() == null) {
					permissionCategoryService.create(permissionCategoryVO);
				} else {
					permissionCategoryService.update(permissionCategoryVO);
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
			boolean ret = permissionCategoryService.delete(id);
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
