package com.analysis.comp.model.repository;

import com.analysis.comp.model.dto.CorpSearchDto;
import com.analysis.comp.model.entity.CorpInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCorpInfoRepository {

    List<CorpInfoEntity> findAllFromCondition(CorpSearchDto corpSearch);
}
