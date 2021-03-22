package com.klb.portal.feignclient;

import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.crud.plant.entity.AdminUser;
import com.klb.portal.crud.plant.vo.UserInfoIdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @author Administrator
 *
 */
@FeignClient(name ="auth" , url = "${feign.auth}")
public interface AuthClient {
	@PostMapping(value = "/admin/adminUser/get")
	R<List<AdminUser>> getAdminUser(@RequestBody UserInfoIdVO vo);

}
