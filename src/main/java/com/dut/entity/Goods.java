package com.dut.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Goods {
    private Long id;
    private Long publisherId;
    private String name;
    private String description;
    private Integer price;
    private Integer category;
    private String tags;
    private String mainImg;
    private String images;
    private Integer status;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime publishTime;
}
