package com.analysis.comp.model.repository;

import com.analysis.comp.model.dto.CorpSearchDto;
import com.analysis.comp.model.entity.CorpInfoEntity;
import com.analysis.comp.model.entity.QCorpInfoEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomCorpInfoRepositoryImpl implements CustomCorpInfoRepository{

    private final JPAQueryFactory queryFactory;

    public CustomCorpInfoRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<CorpInfoEntity> findAllFromCondition(CorpSearchDto corpSearch) {

        Long currentDebt = corpSearch.getCurrentDebt();
        Long netIncome = corpSearch.getNetIncome();
        Long revenue = corpSearch.getRevenue();
        Long operIncome = corpSearch.getOperIncome();
        Long totDebt = corpSearch.getTotDebt();

        QCorpInfoEntity corpInfoEntity = QCorpInfoEntity.corpInfoEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if (currentDebt != null) {
            builder.and(corpInfoEntity.currentDebt.loe(currentDebt));
        }
        if (totDebt != null) {
            builder.and(corpInfoEntity.totDebt.loe(totDebt));
        }
        if (netIncome != null) {
            builder.and(corpInfoEntity.netIncome.goe(netIncome));
        }
        if (revenue != null) {
            builder.and(corpInfoEntity.revenue.goe(revenue));
        }
        if (operIncome != null) {
            builder.and(corpInfoEntity.operIncome.goe(operIncome));
        }

        List<CorpInfoEntity> result = queryFactory.select(corpInfoEntity)
                .from(corpInfoEntity)
                .where(builder)
                .fetch();

        return result;
    }
}
