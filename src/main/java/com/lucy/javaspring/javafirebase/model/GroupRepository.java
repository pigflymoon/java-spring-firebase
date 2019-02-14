package com.lucy.javaspring.javafirebase.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lucy on 8/02/19.
 */
public interface GroupRepository extends JpaRepository<Group,Long> {
    // findByName这个方法表示从数据库中查询Name这个属性等于XXX的所有记录，
    // 类似于SQL语句： select* from xxTable where name=xxx 这种形式
    Group findByName(String name);
    List<Group> findAllByUsersId(String id);

}
