package com.klb.portal.feignclient;

import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.crud.plant.entity.AppDictData;
import com.klb.portal.crud.plant.vo.argsVO.AppDictDataSelectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @author Administrator
 *
 */
@FeignClient(name ="args" , url = "${feign.args}")
public interface ArgsClient {
	@PostMapping(value = "/args/appDictData/list")
	R<List<AppDictData>> listAppDictData(@RequestBody AppDictDataSelectVO vo);

}
