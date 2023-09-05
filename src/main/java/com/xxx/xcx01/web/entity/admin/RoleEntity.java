package com.xxx.xcx01.web.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_role")
public class RoleEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("role_name")
    private String roleName;


}
