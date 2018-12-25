package com.doctorPhone.doctorPhone;

import com.doctorPhone.doctorPhone.config.BrowsePhone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DoctorPhoneApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DoctorPhoneApplication.class, args);
    }
}

