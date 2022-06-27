package com.analysis.comp.model.repository;

import com.analysis.comp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 존재하는 이메일인지 확인
    @Query("select u.email from UserEntity as u where :email = u.email")
    String existByEmail(@Param("email") String email);

    // 유저 패스워드 확인
    @Query("select u.pw from UserEntity as u where :email = u.email")
    String getPwByEmail(@Param("email") String email);

    // 유저 닉네임 확인
    @Query("select u.nickname from UserEntity as u where :email = u.email")
    String getNicknameByEmail(@Param("email") String email);

    // 존재하는 이메일인지 확인
    @Query("select u.userpk from UserEntity as u where :email = u.email")
    String findByEmail(@Param("email") String email);

    @Query("select u from UserEntity as u where :email = u.email")
    Optional<UserEntity> getAllByEmail(@Param("email") String email);
}
