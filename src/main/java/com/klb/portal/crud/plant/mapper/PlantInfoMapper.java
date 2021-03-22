package com.klb.portal.crud.plant.mapper;

import com.klb.portal.crud.plant.entity.PlantInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoAdminListVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoAdminSelectVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoIdVO;
import com.klb.portal.crud.plant.vo.plantInfo.PlantInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
public interface PlantInfoMapper extends BaseMapper<PlantInfo> {

    List<PlantInfoAdminListVO> selectPlantInfoPage(@Param("page")Page<PlantInfoAdminListVO> page, @Param("plantInfoAdminListVO") PlantInfoAdminSelectVO vo);

    PlantInfoVO selectById(@Param("plantInfoIdVO") PlantInfoIdVO vo);

}
