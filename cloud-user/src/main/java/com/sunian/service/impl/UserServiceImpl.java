package com.sunian.service.impl;

import com.sunian.dao.mapper.test1.TfsUserMapper;
import com.sunian.dao.mapper.test2.TfsDeptMapper;
import com.sunian.dao.model.test1.TfsUser;
import com.sunian.dao.model.test2.TfsDept;
import com.sunian.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liull on 2019/7/23.
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    TfsUserMapper tfsUserMapper;

    @Autowired
    TfsDeptMapper tfsDeptMapper;

    @Override
    public Map<String, Object> getAllUser() {
        Map<String,Object> returnMap = new HashMap<>();
        try {
            List<TfsUser> tfsUsers = tfsUserMapper.selectByExample(null);
            returnMap.put("user",tfsUsers);
        }catch (Exception e){
            returnMap.put("respCode","8888");
            returnMap.put("respDesc","getAllUser失败："+e.toString());
            logger.error("getAllUser失败：===",e);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getAllDept() {
        Map<String,Object> returnMap = new HashMap<>();
        try {
            List<TfsDept> tfsDepts = tfsDeptMapper.selectByExample(null);
            returnMap.put("user",tfsDepts);
        }catch (Exception e){
            returnMap.put("respCode","8888");
            returnMap.put("respDesc","getAllDept失败："+e.toString());
            logger.error("getAllDept失败:====",e);
        }
        return returnMap;
    }
}
