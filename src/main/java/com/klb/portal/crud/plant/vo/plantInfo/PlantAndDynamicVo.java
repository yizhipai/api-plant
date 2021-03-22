package com.klb.portal.crud.plant.vo.plantInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 植物信息表
 * </p>
 *
 * @author lpf
 * @since 2021-03-10
 */
@Data
@ApiModel(value="新增植物", description="")
public class PlantAndDynamicVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物名称")
    private String name;

    @ApiModelProperty(value = "植物头像")
    private Integer plantPhotoId;

    @ApiModelProperty(value = "是否公布：1-是；0-否")
    private Integer state;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "动态类型：0-?;1-?")
    private Integer type;

    @ApiModelProperty(value = "拍摄时间")
    private LocalDateTime shootingDate;
}
