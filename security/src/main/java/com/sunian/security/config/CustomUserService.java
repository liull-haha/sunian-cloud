package com.sunian.security.config;

import com.sunian.security.dao.mapper.UsersMapper;
import com.sunian.security.dao.model.Users;
import com.sunian.security.dao.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liull
 * @description
 * @date 2020/12/7
 */
@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails build = null;
        //根据用户名查找用户数据，如果没找到就抛出异常
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Users> users = usersMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(users)){
            Users user = users.get(0);
            //查找权限
            build = User.withUsername(user.getUsername()).password(user.getPassword()).authorities("ROLE_admin").build();
        }
        return build;
    }
}
