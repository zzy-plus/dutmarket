package com.dut.entity;

import lombok.Data;

@Data
public class Goods {
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
