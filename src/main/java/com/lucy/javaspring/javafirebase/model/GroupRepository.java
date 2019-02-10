package com.lucy.javaspring.javafirebase.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lucy on 8/02/19.
 */
public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findByName(String name);
    List<Group> findAllByUsersId(String id);

}
