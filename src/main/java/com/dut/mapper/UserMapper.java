package com.dut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dut.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
