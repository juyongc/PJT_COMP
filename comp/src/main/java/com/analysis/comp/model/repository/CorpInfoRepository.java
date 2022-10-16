package com.analysis.comp.model.repository;

import com.analysis.comp.model.entity.CorpInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CorpInfoRepository extends JpaRepository<CorpInfoEntity, String>, CustomCorpInfoRepository {

}
