package com.dut.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String nickname;
    private String account;
    private String password;
    private String email;
}
