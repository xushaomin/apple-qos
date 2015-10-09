/*package com.appleframework.qos.server.statistics.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/day_stat_code")
public class StatDayCodeController extends BaseController {
	
	@Resource
	private DayStatCodeService dayStatCodeService;
	
	private String viewModel = "day_stat_code/";
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/list")
	public String test01(Model model, Pagination page, HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String consumerAppName = request.getParameter("consumerAppName");
		String providerAppName = request.getParameter("providerAppName");
		
		Date startDay = null;
		Date endDay = null;
		Date now = new Date();
		now.setHours(0);
		now.setMinutes(0);
		now.setSeconds(0);
		
		if(!StringUtils.isEmpty(startDate))
			startDay = DateFormatUtil.toDate(startDate, Constants.pattern10);
		else {
			startDay = now;
			startDay.setDate(1);
		}
		if(!StringUtils.isEmpty(endDate))
			endDay = DateFormatUtil.toDate(endDate, Constants.pattern10);
		
		List<DayStatCode> list = 
				dayStatCodeService.findPageByAppAndDay(page, startDay, endDay, consumerAppName, providerAppName);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("consumerAppName", consumerAppName);
		model.addAttribute("providerAppName", providerAppName);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		if(null != endDay && startDay.getMonth() != endDay.getMonth()) {
			model.addAttribute("errorType", "dayError");
		}
		
		return viewModel + "list";
	}
	
		
}
*/