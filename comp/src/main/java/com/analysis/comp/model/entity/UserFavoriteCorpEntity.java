package com.analysis.comp.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserFavoriteCorpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERPK")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CORP_NAME")
    private CorpInfoEntity corpInfo;

    private LocalDateTime createdDate;

    @PrePersist
    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }

    public UserFavoriteCorpEntity(UserEntity user, CorpInfoEntity corpInfo) {
        setUser(user);
        setCorpInfo(corpInfo);
    }

    public void setUser(UserEntity user) {
        this.user = user;
        if (!user.getFavoriteCorpList().contains(this)) {
            user.addFavoriteCorp(this);
        }
    }

    public void setCorpInfo(CorpInfoEntity corpInfo) {
        this.corpInfo = corpInfo;
    }
}
