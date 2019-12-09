package com.fast.monitorserver.resource;

import com.fast.bpserver.base.BaseResource;
import com.fast.monitorserver.entity.User;
import com.fast.monitorserver.service.IUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nelson on 2019/12/6.
 */

@RestController
@RequestMapping(value = "/user")
public class UserResource extends BaseResource {
    @Autowired
    private IUser userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User findAllUser(@RequestBody User user, HttpServletRequest request ){
        if(user==null||StringUtils.isEmpty(user.getLoginName())||StringUtils.isEmpty(user.getPassword()))return null;
        return userService.findLoginUser(user);
    }
}
