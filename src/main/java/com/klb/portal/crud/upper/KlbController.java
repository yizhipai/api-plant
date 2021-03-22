/**
 * 
 */
package com.klb.portal.crud.upper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.extension.api.ApiController;
//import com.klb.portal.component.redis.RedisService;

/**
 * @author Administrator
 *
 */
public class KlbController extends ApiController {
    protected   Logger logger = LoggerFactory.getLogger(this.getClass());
    
//    @Autowired
//    RedisService redisService;
   
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

//
//    可通过注解 @RequestHeader("token")取值
    public static String getHeaderVal(String key) {
    	
    	return getRequest().getHeader(key);
    }
}
