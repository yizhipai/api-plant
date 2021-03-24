package com.klb.portal.crud.plant.controller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.Contants.Contants;
import com.klb.portal.constants.SysConstant;
import com.klb.portal.crud.plant.entity.PlantDynamic;
import com.klb.portal.crud.plant.entity.PlantFile;
import com.klb.portal.crud.plant.service.IPlantDynamicService;
import com.klb.portal.crud.plant.service.IPlantFileService;
import com.klb.portal.crud.plant.vo.plantInfo.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.klb.portal.domain.MyPage;
import com.klb.portal.crud.plant.entity.PlantInfo;

import org.springframework.beans.factory.annotation.Autowired;
import com.klb.portal.crud.plant.service.IPlantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import com.klb.portal.crud.upper.KlbController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 植物动态 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@RestController
@Api(tags = "PlantInfoController", description = "植物信息")
@RequestMapping("/plant/plantInfo")
public class PlantInfoController extends KlbController {
    @Autowired
    private IPlantInfoService plantInfoService;
    @Autowired
    private IPlantDynamicService plantDynamicService;
    @Autowired
    private IPlantFileService plantFileService;

    @ApiOperation("admin分页查询植物信息-多动态植物展示为一条数据")
    @PostMapping("/adminPage")
    public R<IPage<PlantInfoAdminListVO>> adminPage(@RequestBody MyPage<PlantInfoAdminSelectVO> page) {
        return success(plantInfoService.selectPlantInfoPage(page));
    }

    @ApiOperation("admin查询植物信息详情-一个植物多个动态")
    @PostMapping("/adminGet")
    public R<PlantInfoVO> adminGet(@RequestBody @Valid PlantInfoIdVO vo) {
        return success(plantInfoService.adminGet(vo));
    }

    @ApiOperation("admin删除植物信息")
    @PostMapping("/adminDelete")
    public R<PlantInfoIdVO> adminDelete(@RequestBody @Valid PlantInfoIdVO vo) {
        UpdateWrapper uw = new UpdateWrapper();
        uw.in("id",vo.getId());
        uw.set("is_del", Contants.PLANT_IS_DEL);
        plantInfoService.update(uw);
        return success(vo);
    }


    @ApiOperation("app分页查询本人植物信息")
    @PostMapping("/selfPage")
    public R<IPage<PlantInfoAdminListVO>> selfPage(@RequestHeader(value = SysConstant.KLB_USER_ID)Long userId, @RequestBody MyPage<PlantInfoAdminSelectVO> page) {
        return success(plantInfoService.selfPage(userId,page));
    }

    @ApiOperation("app查询本人植物信息详情-一个植物多个动态(和admin一样)")
    @PostMapping("/selfGet")
    public R<PlantInfoVO> selfGet(@RequestBody @Valid PlantInfoIdVO vo) {
        return success(plantInfoService.adminGet(vo));
    }

    @ApiOperation("app删除本人植物信息")
    @PostMapping("/selfDelete")
    public R<PlantInfoIdVO> selfDelete(@RequestBody @Valid PlantInfoIdVO vo) {
        UpdateWrapper uw = new UpdateWrapper();
        uw.in("id",vo.getId());
        uw.set("is_del", Contants.PLANT_IS_DEL);
        plantInfoService.update(uw);
        return success(vo);
    }

    @ApiOperation("发布植物-首次发布动态需要填写植物基本信息")
    @PostMapping("/add")
    @Transactional
    public R<Boolean> add(@RequestHeader(value = SysConstant.KLB_USER_ID)Long userId, HttpServletRequest request, @RequestParam("files")MultipartFile[] files, @RequestBody @Valid PlantAndDynamicVo vo) {
        //植物基本信息
        PlantInfo plantInfo = new PlantInfo();
        plantInfo.setName(vo.getName());
        plantInfo.setPublishTime(LocalDateTime.now());
        plantInfo.setState(vo.getState());
        plantInfo.setUserId(userId);
        plantInfoService.save(plantInfo);
        //植物动态表
        PlantDynamic plantDynamic = new PlantDynamic();
        plantDynamic.setDescribe(vo.getDescribe());
        plantDynamic.setUserId(userId);
        plantDynamic.setPlantId(plantInfo.getId());
        plantDynamic.setPublishTime(plantInfo.getPublishTime());
        plantDynamic.setType(vo.getType());
        plantDynamic.setShootingDate(vo.getShootingDate());
        plantDynamicService.save(plantDynamic);

        //文件
        List<PlantFile> plantFileList = new ArrayList<>();
        // 多文件上传
        //TODO 文件先传到项目中，后续改成上传到阿里云文件服务器
        String filePath = "";
        String fileRootPath = this.getClass().getResource("/").getPath();
        for (int i=0;i<files.length;i++){
            MultipartFile file = files[i];
            PlantFile plantFile = new PlantFile();
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis()+originalFilename;
            // 存储路径
            filePath = new StringBuilder(fileRootPath)
                    .append("uploadFile/")
                    .append(fileName)
                    .toString();
            plantFile.setFilePath(filePath);
            plantFile.setOriginalFileName(originalFilename);
            plantFile.setFileName(fileName);
            plantFile.setThumbnailFilePath(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/uploadFile/"+fileName);
            plantFile.setUserId(userId);
            plantFile.setPlantId(plantInfo.getId());
            plantFile.setPlantDynamicId(plantDynamic.getId());
            plantFile.setOrderBy(i+1);
            try {
                // 保存文件
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        plantFileService.batchSave(plantFileList);
        if(plantFileList.size()>0){
            plantInfo.setPlantPhotoId(plantFileList.get(0).getId());
        }

        return R.ok(plantInfoService.saveOrUpdate(plantInfo));
    }



/*
  *//**
  * 分页查询
  * 
  *//*
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<IPage<PlantInfo>> pagePlantInfo(@RequestBody MyPage<PlantInfo> page) {
        return success(plantInfoService.page(page, null));
    }
    
  *//**
  * 非分页查询多条数据
  * 
  *//*
    @ApiOperation("非分页查询多条数据")
    @PostMapping("/list")
    public R<List<PlantInfo>> listPlantInfo(PlantInfo plantInfo) {
         
        return null;
    }

  *//**
  * 查询一条数据
  * 
  *//*
    @ApiOperation("查询一条数据")
    @PostMapping("/get")
    public R<PlantInfo> getPlantInfo(PlantInfo plantInfo) {
         
        return null;
    }

  *//**
   * 增加一条数据
   * 
   *//*
    @ApiOperation("增加或修改一条数据")
    @PostMapping("/add")
    public R<String> addPlantInfo(PlantInfo plantInfo) {
         
        return null;
    }
    
    *//**
    * 修改数据
    * 
    *//*
    @ApiOperation("修改数据")
    @PostMapping("/update")
    public R<String> updatePlantInfo(PlantInfo plantInfo) {
         
        return null;
    }
    
    *//**
    * 删除数据
    * 
    *//*
    @ApiOperation("删除数据")
    @PostMapping("/remove")
    public R<String> removePlantInfo(PlantInfo plantInfo) {
         
        return null;
    }
    */
}
