package com.lucy.javaspring.javafirebase.web;

import com.lucy.javaspring.javafirebase.model.Group;
import com.lucy.javaspring.javafirebase.model.GroupRepository;
import com.lucy.javaspring.javafirebase.model.Users;
import com.lucy.javaspring.javafirebase.model.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
//这被称为构造型（stereotype）注解。它为阅读代码的人提供暗示（这是一个支持REST的控制器）
@RestController
//注解提供路由信息，它告诉Spring任何来自"/api"路径的HTTP请求都应该被映射到GroupController
@RequestMapping("/api")
class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository groupRepository;
    private UsersRepository usersRepository;

    public GroupController(GroupRepository groupRepository, UsersRepository usersRepository) {
        this.groupRepository = groupRepository;
        this.usersRepository = usersRepository;
    }
//@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。
// 该注解将HTTP Get 映射到 特定的处理方法上
    @GetMapping("/groups")
    Collection<Group> groups(Principal principal) {

        System.out.println("principal is :" + principal.getName());
        return groupRepository.findAllByUsersId(principal.getName());
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        System.out.println("id  is :" + id);
        System.out.println("group  is :"+group);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group,
                                      @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        // check to see if user already exists
        Optional<Users> users = usersRepository.findById(userId);
        group.setUsers(users.orElse(new Users(userId,
                details.get("name").toString(), details.get("email").toString(),details.get("password").toString())));

        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    @PutMapping("/group")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}