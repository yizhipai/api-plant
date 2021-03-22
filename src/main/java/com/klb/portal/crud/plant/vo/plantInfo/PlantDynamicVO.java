package com.klb.portal.crud.plant.vo.plantInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 植物动态表
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@ApiModel(value="PlantDynamic对象", description="")
@Data
public class PlantDynamicVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物动态表")
    private Integer id;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "动态类型：")
    private Integer type;

    @ApiModelProperty(value = "拍摄时间")
    private LocalDateTime shootingDate;

    private List<PlantFileVo> listPlantFile;
}
