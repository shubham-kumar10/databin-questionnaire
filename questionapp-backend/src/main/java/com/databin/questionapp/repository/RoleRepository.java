package com.databin.questionapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.databin.questionapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
