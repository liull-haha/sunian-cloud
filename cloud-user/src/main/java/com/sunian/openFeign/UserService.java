package com.sunian.openFeign;

import java.util.Map;

/**
 * Created by liull on 2019/7/23.
 */
public interface UserService {
    Map<String,Object> getAllUser();
    Map<String,Object> getAllDept();
}
