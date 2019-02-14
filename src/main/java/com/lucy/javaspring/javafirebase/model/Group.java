package com.lucy.javaspring.javafirebase.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by lucy on 8/02/19.
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user_group")
public class Group {
    //来自JPA的@Id注释用于将Java字段标记为数据库表主键列。
    @Id
    // 数据库将在插入时自动为id字段生成一个值数据到表。
    @GeneratedValue
    private Long id;
    //不能为空的属性上，需要标记为@NotNull
    @NonNull
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Users users;
    //如果是EAGER，那么表示取出这条数据时，它关联的数据也同时取出放入内存中

    //如果是LAZY，那么取出这条数据时，它关联的数据并不取出来，在同一个session中，什么时候要用，就什么时候取(再次访问数据库)。

    //  但是，在session外，就不能再取了。用EAGER时，因为在内存里，所以在session外也可以取。
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> events;
}
