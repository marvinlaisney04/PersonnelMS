package com.hopital.personnelms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hopital.personnelms.model.Personnel;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

}
