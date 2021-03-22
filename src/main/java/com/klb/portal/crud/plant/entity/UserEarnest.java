package com.klb.portal.crud.plant.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户保证金账户
 * </p>
 *
 * @author 34536
 * @since 2020-11-16
 */
@ApiModel(value="UserEarnest对象", description="用户保证金账户")
public class UserEarnest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "卖家保证金")
    private Long sellEarnest;

    @ApiModelProperty(value = "买家保证金")
    private Long buyEarnest;

    @ApiModelProperty(value = "卖家保证金状态 1:锁定 0:未锁")
    private Integer sellStatus;

    @ApiModelProperty(value = "买家保证金状态 1:锁定,0:可使用")
    private Integer buyStatus;

    private LocalDateTime createTime;

    @ApiModelProperty(value = "上次更新时间")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public UserEarnest setId(Long id) {
        this.id = id;
        return this;
    }
    public Long getUserId() {
        return userId;
    }

    public UserEarnest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    public Long getSellEarnest() {
        return sellEarnest;
    }

    public UserEarnest setSellEarnest(Long sellEarnest) {
        this.sellEarnest = sellEarnest;
        return this;
    }
    public Long getBuyEarnest() {
        return buyEarnest;
    }

    public UserEarnest setBuyEarnest(Long buyEarnest) {
        this.buyEarnest = buyEarnest;
        return this;
    }
    public Integer getSellStatus() {
        return sellStatus;
    }

    public UserEarnest setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
        return this;
    }
    public Integer getBuyStatus() {
        return buyStatus;
    }

    public UserEarnest setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public UserEarnest setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public UserEarnest setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "UserEarnest{" +
            "id=" + id +
            ", userId=" + userId +
            ", sellEarnest=" + sellEarnest +
            ", buyEarnest=" + buyEarnest +
            ", sellStatus=" + sellStatus +
            ", buyStatus=" + buyStatus +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
