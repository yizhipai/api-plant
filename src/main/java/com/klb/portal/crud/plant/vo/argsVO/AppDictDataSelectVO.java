package com.klb.portal.crud.plant.vo.argsVO;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 字典常量数据
 * </p>
 *
 * @author 10115
 * @since 2020-11-25
 */
public class AppDictDataSelectVO {

    @ApiModelProperty(value = "字典常量id合集")
    private List<Long> ids;

    @ApiModelProperty(value = "所属于类型")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型标题")
    private String dictTypeTitle;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "系统参数名称")
    private String data;

    @ApiModelProperty(value = "状态 0:停用,1:正常")
    private Integer status;

    @ApiModelProperty(value = "生效开始时间")
    private LocalDateTime effectiveTimeStart;

    @ApiModelProperty(value = "生效结束时间")
    private LocalDateTime effectiveTimeEnd;

    @ApiModelProperty(value = "数据类型 0:系统常量,1:业务设置")
    private Integer dataType;

    @ApiModelProperty(value = "创建开始时间")
    private LocalDateTime createTimeStart;

    @ApiModelProperty(value = "创建开始时间")
    private LocalDateTime createTimeEnd;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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

    public LocalDateTime getEffectiveTimeStart() {
        return effectiveTimeStart;
    }

    public void setEffectiveTimeStart(LocalDateTime effectiveTimeStart) {
        this.effectiveTimeStart = effectiveTimeStart;
    }

    public LocalDateTime getEffectiveTimeEnd() {
        return effectiveTimeEnd;
    }

    public void setEffectiveTimeEnd(LocalDateTime effectiveTimeEnd) {
        this.effectiveTimeEnd = effectiveTimeEnd;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public LocalDateTime getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(LocalDateTime createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public LocalDateTime getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(LocalDateTime createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    @Override
    public String toString() {
        return "AppDictDataSelectVO{" +
                "ids=" + ids +
                ", dictTypeCode='" + dictTypeCode + '\'' +
                ", dictTypeTitle='" + dictTypeTitle + '\'' +
                ", sort=" + sort +
                ", data='" + data + '\'' +
                ", status=" + status +
                ", effectiveTimeStart=" + effectiveTimeStart +
                ", effectiveTimeEnd=" + effectiveTimeEnd +
                ", dataType=" + dataType +
                ", createTimeStart=" + createTimeStart +
                ", createTimeEnd=" + createTimeEnd +
                '}';
    }
}
