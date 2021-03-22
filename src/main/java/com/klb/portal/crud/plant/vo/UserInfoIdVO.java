package com.klb.portal.crud.plant.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author dongyang
 * @since 2020-11-09
 */
public class UserInfoIdVO implements Serializable {

    @NotEmpty
    @ApiModelProperty(value = "用户ID集合",required = true)
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "UserAddrIdVO{" +
                "ids=" + ids +
                '}';
    }
}
