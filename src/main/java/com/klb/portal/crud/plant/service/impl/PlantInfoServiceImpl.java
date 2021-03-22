package com.klb.portal.crud.plant.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.klb.portal.Contants.Contants;
import com.klb.portal.constants.SysConstant;
import com.klb.portal.crud.plant.entity.UserInfo;
import com.klb.portal.crud.plant.mapper.PlantDynamicMapper;
import com.klb.portal.crud.plant.mapper.PlantFileMapper;
import com.klb.portal.crud.plant.vo.UserInfoVO;
import com.klb.portal.crud.plant.vo.UserInfoIdVO;
import com.klb.portal.crud.plant.entity.PlantInfo;
import com.klb.portal.crud.plant.mapper.PlantInfoMapper;
import com.klb.portal.crud.plant.service.IPlantInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.klb.portal.crud.plant.vo.plantInfo.*;
import com.klb.portal.domain.MyPage;
import com.klb.portal.feignclient.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 植物动态 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@Service
public class PlantInfoServiceImpl extends ServiceImpl<PlantInfoMapper, PlantInfo> implements IPlantInfoService {


    @Autowired
    private UserClient userClient;
    @Autowired
    private PlantInfoMapper plantInfoMapper;
    @Autowired
    private PlantDynamicMapper plantDynamicMapper;
    @Autowired
    private PlantFileMapper plantFileMapper;

    @Override
    public PlantInfoVO adminGet(PlantInfoIdVO vo){
        PlantInfoVO plantInfoVO = plantInfoMapper.selectById(vo);
        List<PlantDynamicVO> listPlantDynamic = plantDynamicMapper.listPlanDynamicByPlantId(plantInfoVO.getId());
        List<PlantFileVo> listPlantFile = plantFileMapper.listPlantFileByPlantId(plantInfoVO.getId());

        listPlantDynamic.forEach(o->{
            List<PlantFileVo> list = new ArrayList<>();
            for (int i = 0; i < listPlantFile.size(); ) {
                if(listPlantFile.get(i).getPlantDynamicId().equals(o.getId())){
                    list.add(listPlantFile.get(i));
                    listPlantFile.remove(i);
                    break;
                }
                i++;
            }
            o.setListPlantFile(list);
        });

        plantInfoVO.setListPlantDynamic(listPlantDynamic);
        return plantInfoVO;
    }


    @Override
    public IPage<PlantInfoAdminListVO> selfPage(Long userId,MyPage<PlantInfoAdminSelectVO> myPage){
        Page<PlantInfoAdminListVO> page = new Page<>(myPage.getCurrent(),myPage.getSize());
        PlantInfoAdminSelectVO vo = myPage.getEntity();
        vo.setUserId(userId);
        List<PlantInfoAdminListVO> plantInfoAdminListVOList = plantInfoMapper.selectPlantInfoPage(page,vo);
        page.setRecords(plantInfoAdminListVOList);
        return page;
    }

    @Override
    public IPage<PlantInfoAdminListVO> selectPlantInfoPage(MyPage<PlantInfoAdminSelectVO> myPage){
        Page<PlantInfoAdminListVO> page = new Page<>(myPage.getCurrent(),myPage.getSize());
        PlantInfoAdminSelectVO vo = myPage.getEntity();
        //卖家信息
        if(ObjectUtil.isNotEmpty(vo.getShopPhone())){//用户电话----管理员可以通过电话查询各个人员的植物信息
            //非平台，查用户userId
            if(!vo.getShopPhone().equals(SysConstant.EC_PLATFORM_USER_ID)){//传100表示平台
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(vo.getShopPhone());
                userInfo.setStatus(Contants.STATUS_ABLE);
                R<List<UserInfoVO>> userInfoR = userClient.adminList(userInfo);
                //查询app用户信息返回成功且返回信息存在
                if(String.valueOf(userInfoR.getCode()).equals(String.valueOf(Contants.SUCCESS))
                        && ObjectUtil.isNotEmpty(userInfoR.getData().get(0))){
                    vo.setUserId(userInfoR.getData().get(0).getId());
                }else{//根据手机号未查到用户信息 返回空数据
                    return page;
                }
            }else{//平台用户为SysConstant.EC_PLATFORM_USER_ID
                vo.setUserId(SysConstant.EC_PLATFORM_USER_ID);
            }
        }

        List<PlantInfoAdminListVO> plantInfoAdminListVOList = plantInfoMapper.selectPlantInfoPage(page,vo);
        //遍历插入卖家信息
        UserInfoIdVO userInfoIdVO = new UserInfoIdVO();
        plantInfoAdminListVOList.forEach(x->{
            if(!x.getUserId().toString().equals(SysConstant.EC_PLATFORM_USER_ID.toString())){
                List<Long> ids = new ArrayList<>();
                ids.add(x.getUserId());
                userInfoIdVO.setIds(ids);
            }
        });
        R<List<UserInfoVO>> userInfoR = userClient.getUserInfo(userInfoIdVO);
        //查询app用户信息返回成功且返回信息存在
        plantInfoAdminListVOList.forEach(x->{
            if(!x.getUserId().toString().equals(SysConstant.EC_PLATFORM_USER_ID.toString())){
                if (String.valueOf(userInfoR.getCode()).equals(String.valueOf(Contants.SUCCESS))
                        && ObjectUtil.isNotEmpty(userInfoR.getData().get(0))) {
                    x.setShopPhone(userInfoR.getData().get(0).getPhone());
                }
            }
        });

        page.setRecords(plantInfoAdminListVOList);
        return page;
    }

}
