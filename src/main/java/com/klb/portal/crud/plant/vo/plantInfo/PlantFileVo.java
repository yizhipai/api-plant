package com.klb.portal.crud.plant.vo.plantInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lpf
 * @since 2021-03-05
 */
@Data
@ApiModel(value="PlantFile对象", description="")
public class PlantFileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物图片文件表id")
    private Integer id;

    private String filePath;

    private String fileType;

    private String fileName;

    private String originalFileName;

    private String thumbnailFilePath;

    private Integer userId;

    @ApiModelProperty(value = "植物id（plant_info表）")
    private Integer plantId;

    @ApiModelProperty(value = "动态id（plant_dynamic表）")
    private Integer plantDynamicId;

    private Integer orderBy;
}
