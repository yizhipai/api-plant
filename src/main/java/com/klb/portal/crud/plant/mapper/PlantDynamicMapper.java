package com.klb.portal.crud.plant.mapper;

import com.klb.portal.crud.plant.entity.PlantDynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klb.portal.crud.plant.vo.plantInfo.PlantDynamicVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金穗-大客户部
 * @since 2021-03-05
 */
public interface PlantDynamicMapper extends BaseMapper<PlantDynamic> {
    List<PlantDynamicVO>  listPlanDynamicByPlantId(@Param("id")Long id);

}
