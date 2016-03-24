package com.appleframework.qos.server.statistics.web;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.statistics.model.DayStatMethodSo;
import com.appleframework.qos.server.statistics.model.DayStatMethodVo;
import com.appleframework.qos.server.statistics.service.DayStatMethodService;
import com.appleframework.qos.server.statistics.utils.Constants;
import com.appleframework.qos.server.statistics.utils.DateFormatUtil;
import com.appleframework.qos.server.statistics.utils.DateUtil;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/day_stat_method")
public class StatDayMethodController extends BaseController {
	
	@Resource
	private DayStatMethodService dayStatMethodService;
	
	private String viewModel = "day_stat_method/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, DayStatMethodVo vo, Search se) {
		String startDate = vo.getStartDate();
		String endDate = vo.getEndDate();
		String service = vo.getService();
		String method = vo.getMethod();
		String consumerAppName = vo.getConsumerAppName();
		String providerAppName = vo.getProviderAppName();
		
		Date startDay = null;
		
		Date endDay = null;
		
		if(!StringUtils.isEmpty(startDate)) {
			startDay = DateFormatUtil.toDate(startDate, Constants.pattern10);
		}
		else {
			startDay = DateUtil.getToday();
			startDate = DateFormatUtil.toString(startDay, Constants.pattern10);
		}
		
		if(!StringUtils.isEmpty(endDate))
			endDay = DateFormatUtil.toDate(endDate, Constants.pattern10);
		else {
			endDay = DateUtil.getToday();
			endDate = DateFormatUtil.toString(endDay, Constants.pattern10);
		}
		
		DayStatMethodSo so = DayStatMethodSo.create(startDay, endDay, providerAppName, consumerAppName, service, method);
		page = dayStatMethodService.findPage(page, so, se);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("service", service);
		model.addAttribute("method", method);
		model.addAttribute("page", page);
		model.addAttribute("se", se);
		
		return viewModel + "list";
	}
	
		
}
