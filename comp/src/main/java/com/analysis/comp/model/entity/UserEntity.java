package com.analysis.comp.model.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERPK")
    private Long userpk;
    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String pw;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserFavoriteCorpEntity> favoriteCorpList = new ArrayList<>();


    @Builder
    public UserEntity(String email, String nickname, String pw) {
        this.setEmail(email);
        this.setNickname(nickname);
        this.setPw(pw);
    }

    public void addFavoriteCorp(UserFavoriteCorpEntity favoriteCorp) {
        this.favoriteCorpList.add(favoriteCorp);
        if (favoriteCorp.getUser() != this) {
            favoriteCorp.setUser(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
