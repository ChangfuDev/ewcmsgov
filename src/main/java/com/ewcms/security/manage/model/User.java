/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * <ul>
 * <li>username:用户名
 * <li>password:密码
 * <li>enabled:是否可用(true:可用)
 * <li>accountStart:授权开始时间
 * <li>accountEnd:授权结束时间
 * <li>userInfo:用户信息
 * <li>groups:所属用户组
 * <li>authorities:所属权限
 * </ul>
 *
 * @author wangwei
 */
@Entity
@Table(name = "auth_user")
public class User implements Serializable {

    @Id
    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 40, nullable = false)
    private String password;

    @Column
    private Boolean enabled = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_start")
    private Date accountStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "account_end")
    private Date accountEnd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false)
    private Date createTime = new Date();

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = UserInfo.class,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;

    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinTable(name = "auth_group_members", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "group_name"))
    private Set<Group> groups;

    @ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Authority.class, fetch = FetchType.LAZY)
    @JoinTable(name = "auth_user_authorities", joinColumns =@JoinColumn(name = "username"),inverseJoinColumns =@JoinColumn(name = "authority_name"))
    private Set<Authority> authorities;
    
    public User(){
        
    }
    
    public User(String username){
        this(username,true,null,null);
    }
    
    public User(String username, Boolean enabled,Date accountStart, Date accountEnd) {
        super();
        this.username = username;
        this.enabled = enabled;
        this.accountStart = accountStart;
        this.accountEnd = accountEnd;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getAccountEnd() {
        return accountEnd;
    }

    public void setAccountEnd(Date accountEnd) {
        this.accountEnd = accountEnd;
    }

    public Date getAccountStart() {
        return accountStart;
    }

    public void setAccountStart(Date accountStart) {
        this.accountStart = accountStart;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JsonIgnore
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @JsonIgnore
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("User{");
        builder.append("username:").append(username).append(";");
        builder.append("enabled:").append(enabled).append(";");
        builder.append("accountStart:").append(accountStart).append(";");
        builder.append("accountEnd:").append(accountEnd).append(";");
        builder.append("createTime").append(createTime).append(";");
        
        if (groups != null && !groups.isEmpty()) {
            builder.append("groups:[");
            for (Group group : groups) {
                builder.append(group.getName());
            }
            builder.append("];");
        }

        if(authorities != null && !authorities.isEmpty()) {
            builder.append("authorities:[");
            for (Authority auth : authorities) {
                builder.append(auth.getName());
            }
            builder.append("];");
        }

        builder.append("userInfo").append(userInfo.toString()).append(";");
        builder.append("}");

        return builder.toString();
    }
}
