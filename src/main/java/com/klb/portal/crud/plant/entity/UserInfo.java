package com.klb.portal.crud.plant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klb.portal.utils.LongJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author dongyang
 * @since 2020-11-09
 */
@ApiModel(value="UserInfo对象", description="APP用户信息表")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "-全局ID")
    @TableId(value = "id", type = IdType.INPUT)
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

    @ApiModelProperty(value = "密码")
    private String pwd;

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

    public Long getId() {
        return id;
    }

    public UserInfo setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public UserInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public Integer getSex() {
        return sex;
    }

    public UserInfo setSex(Integer sex) {
        this.sex = sex;
        return this;
    }
    public String getAvator() {
        return avator;
    }

    public UserInfo setAvator(String avator) {
        this.avator = avator;
        return this;
    }
    public String getLinkAddr() {
        return linkAddr;
    }

    public UserInfo setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
        return this;
    }
    public String getPwd() {
        return pwd;
    }

    public UserInfo setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public UserInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Long getInviterId() {
        return inviterId;
    }

    public UserInfo setInviterId(Long inviterId) {
        this.inviterId = inviterId;
        return this;
    }
    public String getSign() {
        return sign;
    }

    public UserInfo setSign(String sign) {
        this.sign = sign;
        return this;
    }
    public String getRole() {
        return role;
    }

    public UserInfo setRole(String role) {
        this.role = role;
        return this;
    }
    public String getOpenid() {
        return openid;
    }

    public UserInfo setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
    public Integer getTop() {
        return top;
    }

    public UserInfo setTop(Integer top) {
        this.top = top;
        return this;
    }
    public String getLabel() {
        return label;
    }

    public UserInfo setLabel(String label) {
        this.label = label;
        return this;
    }
    public String getDeptId() {
        return deptId;
    }

    public UserInfo setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public UserInfo setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public UserInfo setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + id +
            ", name=" + name +
            ", phone=" + phone +
            ", sex=" + sex +
            ", avator=" + avator +
            ", linkAddr=" + linkAddr +
            ", pwd=" + pwd +
            ", status=" + status +
            ", inviterId=" + inviterId +
            ", sign=" + sign +
            ", role=" + role +
            ", openid=" + openid +
            ", top=" + top +
            ", label=" + label +
            ", deptId=" + deptId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
