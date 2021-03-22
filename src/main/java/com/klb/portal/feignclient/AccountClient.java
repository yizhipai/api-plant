package com.klb.portal.feignclient;

import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.constants.SysConstant;
import com.klb.portal.crud.plant.entity.UserEarnest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Administrator
 *
 */
@FeignClient(name ="account" , url = "${feign.account}")
public interface AccountClient {

	@PostMapping(value = "/earnest/userEarnest/getMyEarnest")
	R<UserEarnest> getMyEarnest(@RequestHeader(value = SysConstant.KLB_USER_ID)Long userId);

}
