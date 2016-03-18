package com.appleframework.qos.server.statistics.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.core.entity.AppInfo;
import com.appleframework.qos.server.statistics.service.AppInfoService;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/app_info")
public class AppInfoController extends BaseController {
	
	@Resource
	private AppInfoService appInfoService;
	
	private String viewModel = "app_info/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, HttpServletRequest request) {
		String keyword = request.getParameter("keyword");		
		page = appInfoService.findPage(page, keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody AppInfo get(Model model, Long id, HttpServletRequest request) {
		AppInfo info = appInfoService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		return "app_info/add";
	}
	
	/*@RequestMapping(value = "/save")
	public String save(Model model, AppInfo appInfo, HttpServletRequest request) {
		try {
			appInfoService.insert(appInfo);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}*/
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Long id, HttpServletResponse response) throws Exception {
		AppInfo info = appInfoService.get(id);
		model.addAttribute("info", info);
		return "app_info/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Long id, HttpServletResponse response) throws Exception {
		AppInfo info = appInfoService.get(id);
        model.addAttribute("info", info);
		return "app_info/view";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, AppInfo appInfo, HttpServletResponse response) {
		try {
			AppInfo old = appInfoService.get(appInfo.getId());
			old.setCode(appInfo.getCode());
			old.setName(appInfo.getName());
			old.setRemark(appInfo.getRemark());
			old.setUpdateTime(new Date());
			appInfoService.update(old);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改应用成功", "list");
		return "/commons/success_ajax";
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_code", method = RequestMethod.GET)
	public @ResponseBody String checkRoleName(String oldCode, String code) {
		if (appInfoService.isUniqueByCode(oldCode, code)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
		
}
