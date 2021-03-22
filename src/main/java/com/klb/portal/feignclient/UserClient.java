package com.klb.portal.feignclient;

import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.crud.plant.entity.UserInfo;
import com.klb.portal.crud.plant.vo.UserInfoVO;
import com.klb.portal.crud.plant.vo.UserInfoIdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @author Administrator
 *
 */
@FeignClient(name ="user" , url = "${feign.user}")
public interface UserClient {
	@PostMapping(value = "/app/userInfo/adminGet")
	R<List<UserInfoVO>> getUserInfo(@RequestBody UserInfoIdVO vo);

	@PostMapping(value = "/app/userInfo/adminList")
	R<List<UserInfoVO>> adminList(@RequestBody UserInfo userInfo);


}
