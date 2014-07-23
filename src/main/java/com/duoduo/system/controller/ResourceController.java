package com.duoduo.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.duoduo.core.util.ResponseUtils;
import com.duoduo.core.vo.EasyUiTreeNode;
import com.duoduo.core.vo.Message;
import com.duoduo.core.vo.Page;
import com.duoduo.system.Constants;
import com.duoduo.system.manager.ResourceManager;
import com.duoduo.system.vo.ResourceVO;

/**
 * 菜单Controller
 * @author chengesheng
 * @date 2014-3-19 下午6:18:13
 */
@Controller
@RequestMapping("/system/menu")
public class ResourceController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private ResourceManager resourceService;

	private String listPage = "menu/menu-list";
	private String formPage = "menu/menu-form";
	private String selectFromAllPage = "menu/selectFromAllMenu";

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		return listPage;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ModelMap model) {
		List<ResourceVO> rootMenus = resourceService.listRootMenu();
		model.addAttribute("parentId", Constants.ROOT_MENU_ID);
		model.addAttribute("rootMenuId", Constants.ROOT_MENU_ID);
		model.addAttribute("rootMenus", JSONArray.fromObject(rootMenus).toString());

		return formPage;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String form(ModelMap model, @PathVariable String id) {
		load(model, id);
		return formPage;
	}

	@RequestMapping(value = "/selectFromAllMenu", method = RequestMethod.GET)
	public String selectFromAll() {
		return selectFromAllPage;
	}

	@RequestMapping(value = "/getPageList", method = RequestMethod.POST)
	public void getPageList(HttpServletResponse response, Page<ResourceVO> page,
			@RequestParam(value = "name", required = false) String name) {
		page = resourceService.pagingList(name, page);
		ResponseUtils.renderJson(response, page);
	}

	@RequestMapping(value = "/listAllMenu", method = RequestMethod.POST)
	public void listAll(HttpServletResponse response) {
		List<ResourceVO> menus = resourceService.listAllMenu();
		ResponseUtils.renderJson(response, menus);
	}

	@RequestMapping(value = "/listAllMenuSimple", method = RequestMethod.POST)
	public void listAllSimple(HttpServletResponse response) {
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>(0);
		List<ResourceVO> menus = resourceService.listAllMenuSimple();
		for (ResourceVO menu : menus) {
			if (Constants.ROOT_MENU_ID == menu.getParentId()) {
				EasyUiTreeNode treeNode = new EasyUiTreeNode();
				treeNode.setId(menu.getId());
				treeNode.setText(menu.getName());
				treeNode.setChildren(listChildren(menus, menu.getId()));

				treeNodes.add(treeNode);
			}
		}

		ResponseUtils.renderJson(response, treeNodes);
	}

	/**
	 * 获取指定parentId的下级菜单
	 * @param menus
	 * @param parentId
	 * @return
	 */
	private List<EasyUiTreeNode> listChildren(List<ResourceVO> menus, long parentId) {
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>(0);
		for (ResourceVO menu : menus) {
			if (menu.getParentId() != parentId) {
				continue;
			}

			EasyUiTreeNode treeNode = new EasyUiTreeNode();
			treeNode.setId(menu.getId());
			treeNode.setText(menu.getName());
			treeNode.setChildren(listChildren(menus, menu.getId()));

			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, ResourceVO resourceVO) {
		ResponseUtils.renderJson(response, save(resourceVO));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, String id) {
		ResponseUtils.renderJson(response, delete(id));
	}

	private void load(ModelMap model, String id) {
		ResourceVO resourceVO = resourceService.getById(id);
		model.addAttribute("data", resourceVO);

		List<ResourceVO> rootMenus = resourceService.listRootMenu();
		model.addAttribute("parentId", resourceVO.getParentId());
		model.addAttribute("rootMenuId", Constants.ROOT_MENU_ID);
		model.addAttribute("rootMenus", JSONArray.fromObject(rootMenus).toString());
	}

	private Message<String> save(ResourceVO resourceVO) {
		Message<String> message = new Message<String>(true, "保存成功");

		try {
			int nResult = -1;
			if (resourceVO != null) {
				// 校验菜单名称
				ResourceVO vo = resourceService.getByName(resourceVO.getName());
				if (vo != null && !resourceVO.equals(vo)) {
					message.setSuccess(false);
					message.setDescription("菜单名称已存在");
					return message;
				}

				if (resourceVO.getId() == null) {
					resourceVO = resourceService.create(resourceVO);
				} else {
					resourceService.update(resourceVO);
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
			boolean ret = resourceService.delete(id);
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
