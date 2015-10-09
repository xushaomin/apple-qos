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
import com.appleframework.qos.server.core.entity.MinStat;
import com.appleframework.qos.server.statistics.service.MinStatService;
import com.appleframework.qos.server.statistics.utils.Constants;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/min_stat")
public class StatMinController extends BaseController {
	
	@Resource
	private MinStatService minStatService;
	
	private String viewModel = "min_stat/";
	
	@RequestMapping(value = "/list")
	public String test01(Model model, Pagination page, HttpServletRequest request) {
		String statDate = request.getParameter("statDate");
		String consumerAppName = request.getParameter("consumerAppName");
		String providerAppName = request.getParameter("providerAppName");
		
		Date statDay = null;
		
		if(!StringUtils.isEmpty(statDate))
			statDay = DateFormatUtil.toDate(statDate, Constants.pattern10);
		else
			statDay = DateUtil.getToday();
		
		if(StringUtils.isEmpty(consumerAppName))
			consumerAppName = null;
		if(StringUtils.isEmpty(providerAppName))
			providerAppName = null;
			
		
		List<MinStat> list = minStatService.findPageByAppAndDay(page, statDay, consumerAppName, providerAppName);
		
		model.addAttribute("statDate", statDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		
		return viewModel + "list";
	}
	
		
}
*/