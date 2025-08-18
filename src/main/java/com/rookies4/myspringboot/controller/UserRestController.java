package com.rookies4.myspringboot.controller;

import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserRepository userRepository;

    //Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        System.out.println("생성자 인젝션 " + userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }

    //등록
    @PostMapping
    public UserEntity create(@RequestBody UserEntity user){
        return userRepository.save(user);
    }

    //전체목록 조회
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
    //ID로 조회
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable Long id){
        UserEntity existUser = getUserEntity(id);
        return existUser;
    }
    //Email로 조회하고, 수정
    @PatchMapping("/{email}/")
    public UserEntity updateUser(@PathVariable String email, @RequestBody UserEntity userDetail){
        UserEntity existUser = userRepository.findByEmail(email) //Optional<UserEntity>
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        //name 변경
        existUser.setName(userDetail.getName());
        //DB에 저장
        UserEntity updateUser = userRepository.save(existUser);
        return updateUser;
    }
    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        UserEntity existUser = getUserEntity(id);
        //DB에서 삭제요청
        userRepository.delete(existUser);
        return ResponseEntity.ok("User가 삭제되었습니다.");
    }

    private UserEntity getUserEntity(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        //orElseThrow(Supplier) Supplier의 추상메서드 T get()
        UserEntity existUser = optionalUser
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }
}