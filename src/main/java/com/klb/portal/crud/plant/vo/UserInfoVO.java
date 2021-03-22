package com.klb.portal.crud.plant.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klb.portal.utils.LongJsonSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author dongyang
 * @since 2020-11-09
 */
public class UserInfoVO {

    @ApiModelProperty(value = "用户ID")
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别 0:女,1:男,2:未知")
    private Integer sex;

    @ApiModelProperty(value = "头像url")
    private String avator;

    @ApiModelProperty(value = "联系地址")
    private String linkAddr;

    @ApiModelProperty(value = "状态 0:禁用,1:有效,2:临时锁定,3:锁定,4:销户")
    private Integer status;

    @ApiModelProperty(value = "邀请人ID")
    private Long inviterId;

    @ApiModelProperty(value = "个性签名")
    private String sign;

    @ApiModelProperty(value = "角色 PTYH:普通用户,YYS:园艺师,YXSJ:优选商家")
    private String role;

    @ApiModelProperty(value = "微信OPENID")
    private String openid;

    @ApiModelProperty(value = "置顶0:否,1:是")
    private Integer top;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "卖家保证金")
    private Long sellEarnest;

    @ApiModelProperty(value = "买家保证金")
    private Long buyEarnest;

    @ApiModelProperty(value = "卖家保证金状态 1:锁定 0:未锁")
    private Integer sellStatus;

    @ApiModelProperty(value = "买家保证金状态 1:锁定,0:可使用")
    private Integer buyStatus;

    @ApiModelProperty(value = "资金余额")
    private Long moneyAmt;

    @ApiModelProperty(value = "资金状态 1:正常,0:锁定")
    private Integer moneyStatus;

    @ApiModelProperty(value = "积分余额")
    private Long points;

    @ApiModelProperty(value = "积分余额状态 1:正常,0:锁定")
    private Integer pointsStatus;

    @ApiModelProperty(value = "总得分")
    private Long totalScore=0L;

    @ApiModelProperty(value = "被评价次数")
    private Long nums=0L;

    @ApiModelProperty(value = "平均得分")
    private BigDecimal averageScore=BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSellEarnest() {
        return sellEarnest;
    }

    public void setSellEarnest(Long sellEarnest) {
        this.sellEarnest = sellEarnest;
    }

    public Long getBuyEarnest() {
        return buyEarnest;
    }

    public void setBuyEarnest(Long buyEarnest) {
        this.buyEarnest = buyEarnest;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public Long getMoneyAmt() {
        return moneyAmt;
    }

    public void setMoneyAmt(Long moneyAmt) {
        this.moneyAmt = moneyAmt;
    }

    public Integer getMoneyStatus() {
        return moneyStatus;
    }

    public void setMoneyStatus(Integer moneyStatus) {
        this.moneyStatus = moneyStatus;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Integer getPointsStatus() {
        return pointsStatus;
    }

    public void setPointsStatus(Integer pointsStatus) {
        this.pointsStatus = pointsStatus;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", avator='" + avator + '\'' +
                ", linkAddr='" + linkAddr + '\'' +
                ", status=" + status +
                ", inviterId=" + inviterId +
                ", sign='" + sign + '\'' +
                ", role='" + role + '\'' +
                ", openid='" + openid + '\'' +
                ", top=" + top +
                ", label='" + label + '\'' +
                ", deptId='" + deptId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sellEarnest=" + sellEarnest +
                ", buyEarnest=" + buyEarnest +
                ", sellStatus=" + sellStatus +
                ", buyStatus=" + buyStatus +
                ", moneyAmt=" + moneyAmt +
                ", moneyStatus=" + moneyStatus +
                ", points=" + points +
                ", pointsStatus=" + pointsStatus +
                ", totalScore=" + totalScore +
                ", nums=" + nums +
                ", averageScore=" + averageScore +
                '}';
    }
}
