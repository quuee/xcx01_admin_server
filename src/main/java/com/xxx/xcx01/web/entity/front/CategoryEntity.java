package com.xxx.xcx01.web.entity.front;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


@TableName("t_category")
public class CategoryEntity implements Serializable {

    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;

    @TableField("image_url")
    private String imageUrl;

    @TableField("remark")
    private String remark;

    @TableField("higher_id")
    private Integer higherId;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getHigherId() {
        return higherId;
    }

    public void setHigherId(Integer higherId) {
        this.higherId = higherId;
    }
}
