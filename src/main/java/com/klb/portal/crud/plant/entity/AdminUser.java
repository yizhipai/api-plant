package com.klb.portal.crud.plant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员用户
 * </p>
 *
 * @author 34536
 * @since 2020-11-05
 */
@ApiModel(value="AdminUser对象", description="管理员用户")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "登录账号")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "状态 1:正常,0:锁定")
    private Integer status;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别 0:女,1:男,2:未知")
    private Integer sex;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public AdminUser setId(Long id) {
        this.id = id;
        return this;
    }
    public String getUserName() {
        return userName;
    }

    public AdminUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    public String getNickName() {
        return nickName;
    }

    public AdminUser setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
    public String getPwd() {
        return pwd;
    }

    public AdminUser setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }
    public String getAvatar() {
        return avatar;
    }

    public AdminUser setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public AdminUser setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public AdminUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public AdminUser setEmail(String email) {
        this.email = email;
        return this;
    }
    public Integer getSex() {
        return sex;
    }

    public AdminUser setSex(Integer sex) {
        this.sex = sex;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public AdminUser setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public AdminUser setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
            "id=" + id +
            ", userName=" + userName +
            ", nickName=" + nickName +
            ", pwd=" + pwd +
            ", avatar=" + avatar +
            ", status=" + status +
            ", phone=" + phone +
            ", email=" + email +
            ", sex=" + sex +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
