package com.analysis.comp.model.repository;

import com.analysis.comp.model.entity.TokenEntity;
import com.analysis.comp.model.entity.UserFavoriteCorpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteCorpRepository extends JpaRepository<UserFavoriteCorpEntity, Long> {
}
