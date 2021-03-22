package com.klb.portal.crud.plant.service;

import com.klb.portal.crud.plant.entity.PlantFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 金穗-大客户部
 * @since 2021-03-05
 */
public interface IPlantFileService extends IService<PlantFile> {
    Boolean batchSave(List<PlantFile> plantFileList);
}
