package com.klb.portal.crud.plant.controller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.klb.portal.constants.SysConstant;
import com.klb.portal.crud.plant.entity.PlantFile;
import com.klb.portal.crud.plant.entity.PlantInfo;
import com.klb.portal.crud.plant.service.IPlantFileService;
import com.klb.portal.crud.plant.vo.plantInfo.PlantAndDynamicVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.klb.portal.domain.MyPage;
import com.klb.portal.crud.plant.entity.PlantDynamic;

import org.springframework.beans.factory.annotation.Autowired;
import com.klb.portal.crud.plant.service.IPlantDynamicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import com.klb.portal.crud.upper.KlbController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@RestController
@Api(tags = "PlantDynamicController", description = "")
@RequestMapping("/plant/plantDynamic")
public class PlantDynamicController extends KlbController {
    @Autowired
    private IPlantDynamicService plantDynamicService;
    @Autowired
    private IPlantFileService plantFileService;

    @ApiOperation("发布动态")
    @PostMapping("/add")
    @Transactional
    public R<Boolean> add(@RequestHeader(value = SysConstant.KLB_USER_ID)Long userId, HttpServletRequest request, @RequestParam("files")MultipartFile[] files, @RequestBody @Valid PlantDynamic vo) {

        //植物动态表
        plantDynamicService.save(vo);

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
            plantFile.setPlantId(vo.getPlantId());
            plantFile.setPlantDynamicId(vo.getId());
            plantFile.setOrderBy(i+1);
            try {
                // 保存文件
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return R.ok(plantFileService.batchSave(plantFileList));
    }
 /*
  *//**
  * 分页查询
  * 
  *//*
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<IPage<PlantDynamic>> pagePlantDynamic(@RequestBody MyPage<PlantDynamic> page) {
        return success(plantDynamicService.page(page, null));
    }
    
  *//**
  * 非分页查询多条数据
  * 
  *//*
    @ApiOperation("非分页查询多条数据")
    @PostMapping("/list")
    public R<List<PlantDynamic>> listPlantDynamic(PlantDynamic plantDynamic) {
         
        return null;
    }

  *//**
  * 查询一条数据
  * 
  *//*
    @ApiOperation("查询一条数据")
    @PostMapping("/get")
    public R<PlantDynamic> getPlantDynamic(PlantDynamic plantDynamic) {
         
        return null;
    }

  *//**
   * 增加一条数据
   * 
   *//*
    @ApiOperation("增加或修改一条数据")
    @PostMapping("/add")
    public R<String> addPlantDynamic(PlantDynamic plantDynamic) {
         
        return null;
    }
    
    *//**
    * 修改数据
    * 
    *//*
    @ApiOperation("修改数据")
    @PostMapping("/update")
    public R<String> updatePlantDynamic(PlantDynamic plantDynamic) {
         
        return null;
    }
    
    *//**
    * 删除数据
    * 
    *//*
    @ApiOperation("删除数据")
    @PostMapping("/remove")
    public R<String> removePlantDynamic(PlantDynamic plantDynamic) {
         
        return null;
    }
    */
}
