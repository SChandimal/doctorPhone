package com.doctorPhone.doctorPhone.service;

import com.doctorPhone.doctorPhone.common.Phone_Details;
import com.doctorPhone.doctorPhone.repo.Phone_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Phone_Service {

    @Autowired
    private Phone_Repo phoneRepo;

    public void savePhone(Phone_Details phoneDetails){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+phoneDetails);
        phoneRepo.save(phoneDetails);
    }
}
