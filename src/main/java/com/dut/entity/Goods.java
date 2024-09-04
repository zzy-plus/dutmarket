package com.dut.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Goods {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long publisherId;
    private String name;
    private String description;
    private Integer price;
    private Integer category;
    private String tags;
    private String images;
    private Integer status;
}
