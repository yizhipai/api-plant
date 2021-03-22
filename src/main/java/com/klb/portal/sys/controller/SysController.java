/**
 * 
 */
package com.klb.portal.sys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author hcs
 * 系统级常规服务
 *
 */
@Api(tags = "SysController", description = "系统级服务")
@RestController
@RequestMapping("/sys")
@PropertySource("classpath:version.properties")
public class SysController {
    
	@Value("${version}")
	private String version;
	/**
	 * 取本系统版本号
	 * @return
	 */
	 @ApiOperation(value="系统版本信息", notes="返回信息版本号")
	@GetMapping("/version")
	public String getSysVersion() {	 
		
		return this.version;

	}
}
