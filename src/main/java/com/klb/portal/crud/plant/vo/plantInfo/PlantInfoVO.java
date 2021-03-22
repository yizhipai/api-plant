package com.klb.portal.crud.plant.vo.plantInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author lpf
 * @since 2021-03-09
 */
@ApiModel(value="PlantInfo对象", description="")
@Data
public class PlantInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物基本信息表")
    private Long id;

    @ApiModelProperty(value = "植物名称")
    private String name;

    @ApiModelProperty(value = "是否公布：1-是；0-否")
    private Integer state;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;


    @ApiModelProperty(value = "头像地址")
    private LocalDateTime filePath;

    @ApiModelProperty(value = "头像缩略图地址")
    private LocalDateTime thumbnailFilePath;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "动态信息")
    private List<PlantDynamicVO> listPlantDynamic;
}
