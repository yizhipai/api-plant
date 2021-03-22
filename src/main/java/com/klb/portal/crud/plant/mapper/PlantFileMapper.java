package com.klb.portal.crud.plant.mapper;

import com.klb.portal.crud.plant.entity.PlantFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klb.portal.crud.plant.vo.plantInfo.PlantFileVo;
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
public interface PlantFileMapper extends BaseMapper<PlantFile> {

    List<PlantFileVo> listPlantFileByPlantId(@Param("id")Long id);

    Boolean batchSave(@Param("list")List<PlantFile> plantFileList);
}
