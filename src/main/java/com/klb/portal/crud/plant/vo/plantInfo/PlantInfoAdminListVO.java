package com.klb.portal.crud.plant.vo.plantInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klb.portal.utils.LongJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 植物信息
 * </p>
 *
 * @author lpf
 * @since 2020-03-09
 */
@Data
public class PlantInfoAdminListVO {

    @JsonSerialize(using = LongJsonSerializer.class)

    @ApiModelProperty(value = "植物基本信息表")
    private Integer id;

    @ApiModelProperty(value = "植物名称")
    private String name;

    @ApiModelProperty(value = "是否公布：1-是；0-否")
    private Integer state;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "卖家手机号")
    private String shopPhone;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;


    @ApiModelProperty(value = "头像地址")
    private LocalDateTime filePath;

    @ApiModelProperty(value = "头像缩略图地址")
    private LocalDateTime thumbnailFilePath;
}
