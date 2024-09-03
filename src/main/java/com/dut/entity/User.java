package com.dut.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String nickname;
    private String account;
    private String password;
    private String email;
}
