package com.appleframework.qos.collector.dubbo;

import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceUnavailableException;
import com.appleframework.qos.collector.core.CollectApi;
import com.appleframework.qos.collector.core.URL;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;

import static com.appleframework.qos.collector.core.utils.Constants.KEY_COUNT_PROTOCOL;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_CONSUMER_APP;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_INTERFACE;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_METHOD;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_PROVIDER_ADD;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_FAILURE;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_SUCCESS;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_ELAPSED;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_ERROR_CODE;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_COLLECT_TYPE;
import static com.appleframework.qos.collector.core.utils.Constants.KEY_TIMESTAMP;
import static com.appleframework.qos.collector.core.utils.Constants.COLLECT_TYPE_CONSUMER;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class CollectFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(CollectFilter.class);
	
	// 调用过程拦截
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	if(com.appleframework.qos.collector.core.utils.Constants.COLLECT_START == true) {
            RpcContext context = RpcContext.getContext(); // 提供方必须在invoke()之前获取context信息
            long start = System.currentTimeMillis(); // 记录起始时间戮
            try {
                Result result = invoker.invoke(invocation); // 让调用链往下执行
                if(result.hasException()) {
                	if(result.getException() instanceof AppleException ) {
                		boolean error = false;
                		if(result.getException() instanceof ServiceUnavailableException ) {
                			error = true;
                		}
                		AppleException e = (AppleException)result.getException();
                		String code = e.getCode();
                		collect(invoker, invocation, result, context, start, error, null == code ?"0":code);
                	}
                	else {
                		collect(invoker, invocation, result, context, start, true, String.valueOf(RpcException.BIZ_EXCEPTION + 1));
                	}
                }
                else {
                	collect(invoker, invocation, result, context, start, false, "0");
                }
                return result;
            } catch (RpcException e) {
                collect(invoker, invocation, null, context, start, true, String.valueOf(e.getCode() + 1));
                throw e;
            }
        } else {
            return invoker.invoke(invocation);
        }
    }
    
    // 信息采集
    private void collect(Invoker<?> invoker, Invocation invocation, Result result, RpcContext context, 
    		long start, boolean error, String errorCode) {
        try {
            // ---- 服务信息获取 ----
        	Date date = new Date(start);
            long elapsed = System.currentTimeMillis() - start; // 计算调用耗时
            String application = invoker.getUrl().getParameter(Constants.APPLICATION_KEY);
            String service = invoker.getInterface().getName(); // 获取服务名称
            String method = RpcUtils.getMethodName(invocation); // 获取方法名
            
            if (Constants.CONSUMER_SIDE.equals(invoker.getUrl().getParameter(Constants.SIDE_KEY))) {
                // ---- 服务消费方监控 ----
                //context = RpcContext.getContext(); // 消费方必须在invoke()之后获取context信息
                
                int localPort = 0;
                String remoteValue = invoker.getUrl().getAddress();
                int type = COLLECT_TYPE_CONSUMER;
                
                URL url = new URL(
                		KEY_COUNT_PROTOCOL,
                        NetUtils.getLocalHost(), localPort, 
                        KEY_CONSUMER_APP, application,
                        KEY_INTERFACE, service,
                        KEY_METHOD, method,
                        KEY_PROVIDER_ADD, remoteValue,
                        error ? KEY_FAILURE : KEY_SUCCESS, "1",
                    	KEY_ELAPSED, String.valueOf(elapsed),
                    	KEY_ERROR_CODE, errorCode,
                        KEY_COLLECT_TYPE, String.valueOf(type),
                        KEY_TIMESTAMP, DateFormatUtils.toString(date, DateFormatUtils.pattern12));
                CollectApi.collect(url);
            } 
            
        } catch (Throwable t) {
            logger.error("Failed to monitor count service " + invoker.getUrl() + ", cause: " + t.getMessage(), t);
        }
    }
    
}