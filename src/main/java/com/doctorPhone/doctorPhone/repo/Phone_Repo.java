package com.doctorPhone.doctorPhone.repo;

import com.doctorPhone.doctorPhone.common.Phone_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Phone_Repo extends JpaRepository<Phone_Details , Integer> {

}
