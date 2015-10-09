/*package com.appleframework.qos.server.statistics.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.core.entity.DayStatApp;
import com.appleframework.qos.server.statistics.service.DayStatAppService;
import com.appleframework.qos.server.statistics.utils.Constants;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/day_stat_app")
public class StatDayAppController extends BaseController {
	
	@Resource
	private DayStatAppService dayStatAppService;
	
	private String viewModel = "day_stat_app/";
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String consumerAppName = request.getParameter("consumerAppName");
		String providerAppName = request.getParameter("providerAppName");
		
		Date startDay = null;
		Date endDay = null;
		
		if(!StringUtils.isEmpty(startDate))
			startDay = DateFormatUtil.toDate(startDate, Constants.pattern10);
		if(!StringUtils.isEmpty(endDate))
			endDay = DateFormatUtil.toDate(endDate, Constants.pattern10);
		
		if(StringUtils.isEmpty(consumerAppName))
			consumerAppName = null;
		if(StringUtils.isEmpty(providerAppName))
			providerAppName = null;
			
		
		List<DayStatApp> list = dayStatAppService.findPageByAppAndBetweenDay(page, startDay, endDay, 
				consumerAppName, providerAppName);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		if(null != endDay && startDay.getMonth() != endDay.getMonth()) {
			model.addAttribute("errorType", "dayError");
		}
		
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/rank")
	public String rank(Model model, Pagination page, HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String consumerAppName = request.getParameter("consumerAppName");
		String providerAppName = request.getParameter("providerAppName");
		
		Date startDay = null;
		
		if(!StringUtil.isEmpty(startDate))
			startDay = DateFormatUtil.toDate(startDate, Constants.pattern10);
		else
			startDay = DateUtil.getToday();
		
		if(StringUtil.isEmpty(consumerAppName))
			consumerAppName = null;
		if(StringUtil.isEmpty(providerAppName))
			providerAppName = null;
			
		
		List<DayStatApp> list = dayStatAppService.findPageByAppAndDay(page, startDay, 
				consumerAppName, providerAppName);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		
		return viewModel + "rank";
	}
	
		
}
*/