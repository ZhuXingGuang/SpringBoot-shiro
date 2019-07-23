package com.zxg.plustest.service.impl;

import com.zxg.plustest.entity.User;
import com.zxg.plustest.mapper.UserMapper;
import com.zxg.plustest.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxg
 * @since 2019-07-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
