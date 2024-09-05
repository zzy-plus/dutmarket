package com.dut.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Goods {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
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
