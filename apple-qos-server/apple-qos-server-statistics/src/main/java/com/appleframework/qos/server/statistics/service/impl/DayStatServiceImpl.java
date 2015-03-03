package com.appleframework.qos.server.statistics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.qos.server.core.entity.DayStatApp;
import com.appleframework.qos.server.core.entity.DayStatCode;
import com.appleframework.qos.server.core.entity.DayStatMethod;
import com.appleframework.qos.server.core.entity.DayStatNode;
import com.appleframework.qos.server.core.entity.MinCollect;
import com.appleframework.qos.server.statistics.model.DaySumApp;
import com.appleframework.qos.server.statistics.model.DaySumCode;
import com.appleframework.qos.server.statistics.model.DaySumMethod;
import com.appleframework.qos.server.statistics.model.DaySumNode;
import com.appleframework.qos.server.statistics.service.DayStatCodeService;
import com.appleframework.qos.server.statistics.service.DayStatMethodService;
import com.appleframework.qos.server.statistics.service.DayStatNodeService;
import com.appleframework.qos.server.statistics.service.MinCollectService;
import com.appleframework.qos.server.statistics.service.DayStatAppService;
import com.appleframework.qos.server.statistics.service.DayStatService;

@Service("dayStatService")
public class DayStatServiceImpl implements DayStatService {

	@Resource
	private DayStatAppService dayStatAppService;
	
	@Resource
	private DayStatCodeService dayStatCodeService;
	
	@Resource
	private DayStatNodeService dayStatNodeService;
	
	@Resource
	private DayStatMethodService dayStatMethodService;

	@Resource
	private MinCollectService minCollectService;
	
	
	/*@Override
	public void createTable(String month) {
		dayStatAppService.createTable(month);
		dayStatCodeService.createTable(month);
		dayStatNodeService.createTable(month);
		dayStatMethodService.createTable(month);
	}*/

	public void app(Date statDate) {
		List<MinCollect> list = minCollectService.findAppByDate(statDate);
		for (MinCollect minCollect : list) {

			Long providerAppId = minCollect.getProviderAppId();
			Long consumerAppId = minCollect.getConsumerAppId();
			String providerAppName = minCollect.getProviderAppName();
			String consumerAppName = minCollect.getConsumerAppName();

			long failSumNumber = 0L;
			long failSumElapsed = 0L;
			long failMaxElapsed = 0L;
			long failAvgElapsed = 0L;

			long succSumNumber = 0L;
			long succSumElapsed = 0L;
			long succMaxElapsed = 0L;
			long succAvgElapsed = 0L;

			long totalSumElapsed = 0L;
			long totalSumNumber = 0L;
			long totalAvgElapsed = 0L;
			double totalSuccPer = 0D;

			DaySumApp daySumAppSuccess = minCollectService.sumSuccessByApp(
					statDate, consumerAppId, providerAppId);
			DaySumApp daySumAppFailure = minCollectService.sumFailureByApp(
					statDate, consumerAppId, providerAppId);

			if (null != daySumAppSuccess) {
				succSumNumber = daySumAppSuccess.getSuccess();
				succSumElapsed = daySumAppSuccess.getElapsed();
				succMaxElapsed = daySumAppSuccess.getMaxElapsed();
				if (succSumNumber != 0)
					succAvgElapsed = succSumElapsed / succSumNumber;
			}

			if (null != daySumAppFailure) {
				failSumNumber = daySumAppFailure.getFailure();
				failSumElapsed = daySumAppFailure.getElapsed();
				failMaxElapsed = daySumAppFailure.getMaxElapsed();
				if (failSumNumber != 0)
					failAvgElapsed = failSumElapsed / failSumNumber;
			}

			totalSumNumber = succSumNumber + failSumNumber;
			totalSumElapsed = succSumElapsed + failSumElapsed;
			totalSuccPer = (double)succSumNumber / (double)totalSumNumber;
			totalAvgElapsed = totalSumElapsed / totalSumNumber;

			DayStatApp dayStatApp = dayStatAppService.getByDate(statDate,
					consumerAppId, providerAppId);

			if (null == dayStatApp) {

				dayStatApp = new DayStatApp();
				dayStatApp.setConsumerAppId(consumerAppId);
				dayStatApp.setConsumerAppName(consumerAppName);
				dayStatApp.setProviderAppId(providerAppId);
				dayStatApp.setProviderAppName(providerAppName);
				dayStatApp.setStatDate(statDate);

				dayStatApp.setSuccAvgElapsed(succAvgElapsed);
				dayStatApp.setSuccMaxElapsed(succMaxElapsed);
				dayStatApp.setSuccSumElapsed(succSumElapsed);
				dayStatApp.setSuccSumNumber(succSumNumber);

				dayStatApp.setFailAvgElapsed(failAvgElapsed);
				dayStatApp.setFailMaxElapsed(failMaxElapsed);
				dayStatApp.setFailSumElapsed(failSumElapsed);
				dayStatApp.setFailSumNumber(failSumNumber);

				dayStatApp.setTotalSuccPer(totalSuccPer);
				dayStatApp.setTotalSumElapsed(totalSumElapsed);
				dayStatApp.setTotalSumNumber(totalSumNumber);
				dayStatApp.setTotalAvgElapsed(totalAvgElapsed);

				dayStatAppService.save(dayStatApp);
			} else {

				dayStatApp.setSuccAvgElapsed(succAvgElapsed);
				dayStatApp.setSuccMaxElapsed(succMaxElapsed);
				dayStatApp.setSuccSumElapsed(succSumElapsed);
				dayStatApp.setSuccSumNumber(succSumNumber);

				dayStatApp.setFailAvgElapsed(failAvgElapsed);
				dayStatApp.setFailMaxElapsed(failMaxElapsed);
				dayStatApp.setFailSumElapsed(failSumElapsed);
				dayStatApp.setFailSumNumber(failSumNumber);

				dayStatApp.setTotalSuccPer(totalSuccPer);
				dayStatApp.setTotalSumElapsed(totalSumElapsed);
				dayStatApp.setTotalSumNumber(totalSumNumber);
				dayStatApp.setTotalAvgElapsed(totalAvgElapsed);

				dayStatAppService.update(dayStatApp);
			}

		}
	}
	
	public void node(Date statDate) {
		List<MinCollect> list = minCollectService.findNodeByDate(statDate);
		for (MinCollect minCollect : list) {

			Long providerAppId = minCollect.getProviderAppId();
			Long consumerAppId = minCollect.getConsumerAppId();
			String providerAppName = minCollect.getProviderAppName();
			String consumerAppName = minCollect.getConsumerAppName();
			Long providerNodeId = minCollect.getProviderNodeId();
			String providerNodeIp = minCollect.getProviderAddr();

			long totalSumNumber = 0L;

			DaySumNode daySumNode = minCollectService.sumNodeByApp(statDate, consumerAppId, providerAppId, providerNodeId);

			if (null != daySumNode) {
				totalSumNumber = daySumNode.getFailure() + daySumNode.getSuccess();
			}

			DayStatNode dayStatNode = dayStatNodeService.getByDate(statDate,
					consumerAppId, providerAppId, providerNodeId);

			if (null == dayStatNode) {
				dayStatNode = new DayStatNode();
				dayStatNode.setConsumerAppId(consumerAppId);
				dayStatNode.setConsumerAppName(consumerAppName);
				dayStatNode.setProviderAppId(providerAppId);
				dayStatNode.setProviderAppName(providerAppName);
				dayStatNode.setStatDate(statDate);
				dayStatNode.setProviderNodeId(providerNodeId);
				dayStatNode.setProviderNodeIp(providerNodeIp);
				dayStatNode.setTotalSumNumber(totalSumNumber);
				dayStatNodeService.save(dayStatNode);
			} else {
				dayStatNode.setTotalSumNumber(totalSumNumber);
				dayStatNodeService.update(dayStatNode);
			}

		}
	}
	

	public void code(Date statDate) {
		List<MinCollect> list = minCollectService.findCodeByDate(statDate);
		for (MinCollect minCollect : list) {

			Long providerAppId = minCollect.getProviderAppId();
			Long consumerAppId = minCollect.getConsumerAppId();
			String providerAppName = minCollect.getProviderAppName();
			String consumerAppName = minCollect.getConsumerAppName();
			String errorCode = minCollect.getErrorCode();

			long totalSumNumber = 0L;

			DaySumCode daySumCode = minCollectService.sumCodeByApp(statDate, consumerAppId, providerAppId, errorCode);

			if (null != daySumCode) {
				totalSumNumber = daySumCode.getFailure() + daySumCode.getSuccess();
			}

			DayStatCode dayStatCode = dayStatCodeService.getByDate(statDate,
					consumerAppId, providerAppId, errorCode);

			if (null == dayStatCode) {
				dayStatCode = new DayStatCode();
				dayStatCode.setConsumerAppId(consumerAppId);
				dayStatCode.setConsumerAppName(consumerAppName);
				dayStatCode.setProviderAppId(providerAppId);
				dayStatCode.setProviderAppName(providerAppName);
				dayStatCode.setStatDate(statDate);
				dayStatCode.setErrorCode(errorCode);
				dayStatCode.setTotalSumNumber(totalSumNumber);
				dayStatCodeService.save(dayStatCode);
			} else {
				dayStatCode.setTotalSumNumber(totalSumNumber);
				dayStatCodeService.update(dayStatCode);
			}
		}
	}
	
	

	public void method(Date statDate) {
		List<MinCollect> list = minCollectService.findMethodByDate(statDate);
		for (MinCollect minCollect : list) {

			Long providerAppId = minCollect.getProviderAppId();
			Long consumerAppId = minCollect.getConsumerAppId();
			
			String service = minCollect.getService();
			String method = minCollect.getMethod();
			
			String providerAppName = minCollect.getProviderAppName();
			String consumerAppName = minCollect.getConsumerAppName();

			long failSumNumber = 0L;
			long failSumElapsed = 0L;
			long failMaxElapsed = 0L;
			long failAvgElapsed = 0L;

			long succSumNumber = 0L;
			long succSumElapsed = 0L;
			long succMaxElapsed = 0L;
			long succAvgElapsed = 0L;

			long totalSumElapsed = 0L;
			long totalSumNumber = 0L;
			long totalAvgElapsed = 0L;
			double totalSuccPer = 0D;

			DaySumMethod daySumServiceSuccess = minCollectService.sumSuccessByMethod(
					statDate, consumerAppId, providerAppId, service, method);
			DaySumMethod daySumServiceFailure = minCollectService.sumFailureByMethod(
					statDate, consumerAppId, providerAppId, service, method);

			if (null != daySumServiceSuccess) {
				succSumNumber = daySumServiceSuccess.getSuccess();
				succSumElapsed = daySumServiceSuccess.getElapsed();
				succMaxElapsed = daySumServiceSuccess.getMaxElapsed();
				if (succSumNumber != 0)
					succAvgElapsed = succSumElapsed / succSumNumber;
			}

			if (null != daySumServiceFailure) {
				failSumNumber = daySumServiceFailure.getFailure();
				failSumElapsed = daySumServiceFailure.getElapsed();
				failMaxElapsed = daySumServiceFailure.getMaxElapsed();
				if (failSumNumber != 0)
					failAvgElapsed = failSumElapsed / failSumNumber;
			}

			totalSumNumber = succSumNumber + failSumNumber;
			totalSumElapsed = succSumElapsed + failSumElapsed;
			totalSuccPer = (double)succSumNumber / (double)totalSumNumber;
			totalAvgElapsed = totalSumElapsed / totalSumNumber;

			DayStatMethod dayStatMethod = dayStatMethodService.getByDate(statDate, consumerAppId, providerAppId,
					service, method);

			if (null == dayStatMethod) {
				dayStatMethod = new DayStatMethod();
				dayStatMethod.setConsumerAppId(consumerAppId);
				dayStatMethod.setConsumerAppName(consumerAppName);
				dayStatMethod.setProviderAppId(providerAppId);
				dayStatMethod.setProviderAppName(providerAppName);
				dayStatMethod.setService(service);
				dayStatMethod.setMethod(method);
				dayStatMethod.setStatDate(statDate);

				dayStatMethod.setSuccAvgElapsed(succAvgElapsed);
				dayStatMethod.setSuccMaxElapsed(succMaxElapsed);
				dayStatMethod.setSuccSumElapsed(succSumElapsed);
				dayStatMethod.setSuccSumNumber(succSumNumber);

				dayStatMethod.setFailAvgElapsed(failAvgElapsed);
				dayStatMethod.setFailMaxElapsed(failMaxElapsed);
				dayStatMethod.setFailSumElapsed(failSumElapsed);
				dayStatMethod.setFailSumNumber(failSumNumber);

				dayStatMethod.setTotalSuccPer(totalSuccPer);
				dayStatMethod.setTotalSumElapsed(totalSumElapsed);
				dayStatMethod.setTotalSumNumber(totalSumNumber);
				dayStatMethod.setTotalAvgElapsed(totalAvgElapsed);

				dayStatMethodService.save(dayStatMethod);
			} else {

				dayStatMethod.setSuccAvgElapsed(succAvgElapsed);
				dayStatMethod.setSuccMaxElapsed(succMaxElapsed);
				dayStatMethod.setSuccSumElapsed(succSumElapsed);
				dayStatMethod.setSuccSumNumber(succSumNumber);

				dayStatMethod.setFailAvgElapsed(failAvgElapsed);
				dayStatMethod.setFailMaxElapsed(failMaxElapsed);
				dayStatMethod.setFailSumElapsed(failSumElapsed);
				dayStatMethod.setFailSumNumber(failSumNumber);

				dayStatMethod.setTotalSuccPer(totalSuccPer);
				dayStatMethod.setTotalSumElapsed(totalSumElapsed);
				dayStatMethod.setTotalSumNumber(totalSumNumber);
				dayStatMethod.setTotalAvgElapsed(totalAvgElapsed);

				dayStatMethodService.update(dayStatMethod);
			}

		}
	}

}
