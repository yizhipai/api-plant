package com.klb.portal.crud.plant.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 金穗-大客户部
 * @since 2021-03-05
 */
@Data
@ApiModel(value="PlantDynamic对象", description="")
public class PlantDynamic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物动态表")
    private Long id;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "植物id")
    private Long plantId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "动态类型：")
    private Integer type;

    @ApiModelProperty(value = "拍摄时间")
    private LocalDateTime shootingDate;
}
