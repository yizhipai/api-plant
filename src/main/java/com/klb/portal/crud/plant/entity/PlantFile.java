package com.klb.portal.crud.plant.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 植物图片文件表
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@Data
@ApiModel(value="PlantFile对象", description="")
public class PlantFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物图片文件表id")
    private Long id;

    private String filePath;

    private String fileType;

    private String fileName;

    private String originalFileName;

    private String thumbnailFilePath;

    private Long userId;

    @ApiModelProperty(value = "植物id（plant_info表）")
    private Long plantId;

    @ApiModelProperty(value = "动态id（plant_dynamic表）")
    private Long plantDynamicId;

    @ApiModelProperty(value = "排序")
    private Integer orderBy;
}
