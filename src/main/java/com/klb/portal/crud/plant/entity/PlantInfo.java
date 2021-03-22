package com.klb.portal.crud.plant.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 植物信息表
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@Data
@ApiModel(value="PlantInfo对象", description="")
public class PlantInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物基本信息表")
    private Long id;

    @ApiModelProperty(value = "植物名称")
    private String name;

    @ApiModelProperty(value = "植物头像")
    private Long plantPhotoId;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "是否公布：1-是；0-否")
    private Integer state;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime lastUpdateTime;
}
