package com.klb.portal.crud.plant.controller;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestBody;
import com.klb.portal.domain.MyPage;
import com.klb.portal.crud.plant.entity.PlantFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.klb.portal.crud.plant.service.IPlantFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import org.springframework.web.bind.annotation.RestController;
import com.klb.portal.crud.upper.KlbController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@RestController
@Api(tags = "PlantFileController", description = "")
@RequestMapping("/plant/plantFile")
public class PlantFileController extends KlbController {
    @Autowired
    private IPlantFileService plantFileService;
    
 /* *//**
  * 分页查询
  * 
  *//*
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<IPage<PlantFile>> pagePlantFile(@RequestBody MyPage<PlantFile> page) {
        return success(plantFileService.page(page, null));
    }
    
  *//**
  * 非分页查询多条数据
  * 
  *//*
    @ApiOperation("非分页查询多条数据")
    @PostMapping("/list")
    public R<List<PlantFile>> listPlantFile(PlantFile plantFile) {
         
        return null;
    }

  *//**
  * 查询一条数据
  * 
  *//*
    @ApiOperation("查询一条数据")
    @PostMapping("/get")
    public R<PlantFile> getPlantFile(PlantFile plantFile) {
         
        return null;
    }

  *//**
   * 增加一条数据
   * 
   *//*
    @ApiOperation("增加或修改一条数据")
    @PostMapping("/add")
    public R<String> addPlantFile(PlantFile plantFile) {
         
        return null;
    }
    
    *//**
    * 修改数据
    * 
    *//*
    @ApiOperation("修改数据")
    @PostMapping("/update")
    public R<String> updatePlantFile(PlantFile plantFile) {
         
        return null;
    }
    
    *//**
    * 删除数据
    * 
    *//*
    @ApiOperation("删除数据")
    @PostMapping("/remove")
    public R<String> removePlantFile(PlantFile plantFile) {
         
        return null;
    }*/
    
}
