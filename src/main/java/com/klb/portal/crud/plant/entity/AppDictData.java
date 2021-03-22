package com.klb.portal.crud.plant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典常量数据
 * </p>
 *
 * @author 10115
 * @since 2020-11-25
 */
@ApiModel(value="AppDictData对象", description="字典常量数据")
@TableName(autoResultMap = true)
public class AppDictData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属于类型")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型标题")
    private String dictTypeTitle;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "JSON 系统参数 label/val/sort ")
    private String data;

    @ApiModelProperty(value = "状态 0:停用,1:正常")
    private Integer status;

    @ApiModelProperty(value = "生效时间")
    private LocalDateTime effectiveTime;

    @ApiModelProperty(value = "数据类型 0:系统常量,1:业务设置")
    private Integer dataType;

    private LocalDateTime createTime;

    private String remark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictTypeTitle() {
        return dictTypeTitle;
    }

    public void setDictTypeTitle(String dictTypeTitle) {
        this.dictTypeTitle = dictTypeTitle;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(LocalDateTime effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AppDictData{" +
                "id=" + id +
                ", dictTypeCode='" + dictTypeCode + '\'' +
                ", dictTypeTitle='" + dictTypeTitle + '\'' +
                ", sort=" + sort +
                ", data='" + data + '\'' +
                ", status=" + status +
                ", effectiveTime=" + effectiveTime +
                ", dataType=" + dataType +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
