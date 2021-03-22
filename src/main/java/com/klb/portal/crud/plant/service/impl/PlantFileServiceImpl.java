package com.klb.portal.crud.plant.service.impl;

import com.klb.portal.crud.plant.entity.PlantFile;
import com.klb.portal.crud.plant.mapper.PlantFileMapper;
import com.klb.portal.crud.plant.service.IPlantFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 金穗-大客户部
 * @since 2021-03-05
 */
@Service
public class PlantFileServiceImpl extends ServiceImpl<PlantFileMapper, PlantFile> implements IPlantFileService {

    @Autowired
    private PlantFileMapper plantFileMapper;

    @Override
    public Boolean batchSave(List<PlantFile> plantFileList) {
       return plantFileMapper.batchSave(plantFileList);
    }
}
