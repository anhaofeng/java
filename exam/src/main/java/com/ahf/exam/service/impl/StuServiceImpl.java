package com.ahf.exam.service.impl;

import com.ahf.exam.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl  {
    @Autowired
    private  IStuService stuService;


    public boolean login(String uname, String password) {
        boolean exist=false;
       if (stuService.stuLogin(uname,password)!= null){
           exist=true;
       }
        return exist;
    }
}
