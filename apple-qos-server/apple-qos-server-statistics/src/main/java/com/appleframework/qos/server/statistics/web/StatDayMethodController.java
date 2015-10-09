package com.appleframework.qos.server.statistics.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.statistics.service.DayStatMethodService;
import com.appleframework.qos.server.statistics.utils.Constants;
import com.appleframework.qos.server.statistics.utils.DateFormatUtil;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/day_stat_method")
public class StatDayMethodController extends BaseController {
	
	@Resource
	private DayStatMethodService dayStatMethodService;
	
	private String viewModel = "day_stat_method/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String service = request.getParameter("service");
		String method = request.getParameter("method");
		String consumerAppName = request.getParameter("consumerAppName");
		String providerAppName = request.getParameter("providerAppName");
		
		Date startDay = null;
		
		Date endDay = null;
		
		if(!StringUtils.isEmpty(startDate)) {
			startDay = DateFormatUtil.toDate(startDate, Constants.pattern10);
		}
		else {
			startDay = new Date();
			startDate = DateFormatUtil.toString(startDay, Constants.pattern10);
		}
		
		if(!StringUtils.isEmpty(endDate))
			endDay = DateFormatUtil.toDate(endDate, Constants.pattern10);
		else {
			endDay = new Date();
			endDate = DateFormatUtil.toString(endDay, Constants.pattern10);
		}
		
		page = dayStatMethodService.findPage(page, startDay, endDay, consumerAppName, providerAppName, 
					service, method);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("service", service);
		model.addAttribute("method", method);
		model.addAttribute("page", page);
		
		return viewModel + "list";
	}
	
		
}
