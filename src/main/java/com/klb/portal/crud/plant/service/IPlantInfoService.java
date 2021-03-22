package com.klb.portal.crud.plant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.crud.plant.entity.PlantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoAdminListVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoAdminSelectVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoIdVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoVO;
import com.klb.portal.domain.MyPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金穗-大客户部
 * @since 2021-03-05
 */
public interface IPlantInfoService extends IService<PlantInfo> {

    IPage<PlantInfoAdminListVO> selectPlantInfoPage(MyPage<PlantInfoAdminSelectVO> myPage);
    IPage<PlantInfoAdminListVO> selfPage(Long userId,MyPage<PlantInfoAdminSelectVO> myPage);
    PlantInfoVO adminGet(PlantInfoIdVO vo);

}
