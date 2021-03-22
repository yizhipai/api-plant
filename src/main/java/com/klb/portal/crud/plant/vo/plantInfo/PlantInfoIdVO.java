package com.klb.portal.crud.plant.vo.plantInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 植物信息
 * </p>
 *
 * @author lpf
 * @since 2020-03-09
 */
@Data
public class PlantInfoIdVO {

    @NotEmpty
    @ApiModelProperty(value = "植物id",required = true)
    private Long id;
}
