package com.analysis.comp.model.repository;

import com.analysis.comp.model.entity.CorpCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorpCodeRepository extends JpaRepository<CorpCodeEntity, String> {

    @Query("select c.corpName from CorpCodeEntity c where c.corpName like :name%")
    List<String> checkNameAuto(String name);
}
