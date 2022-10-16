package com.analysis.comp.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorpInfoEntity is a Querydsl query type for CorpInfoEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCorpInfoEntity extends EntityPathBase<CorpInfoEntity> {

    private static final long serialVersionUID = -131659705L;

    public static final QCorpInfoEntity corpInfoEntity = new QCorpInfoEntity("corpInfoEntity");

    public final NumberPath<Long> befTax = createNumber("befTax", Long.class);

    public final NumberPath<Long> circulateNum = createNumber("circulateNum", Long.class);

    public final StringPath corpCode = createString("corpCode");

    public final StringPath corpName = createString("corpName");

    public final NumberPath<Long> currentAsset = createNumber("currentAsset", Long.class);

    public final NumberPath<Long> currentDebt = createNumber("currentDebt", Long.class);

    public final NumberPath<Long> netIncome = createNumber("netIncome", Long.class);

    public final NumberPath<Long> operIncome = createNumber("operIncome", Long.class);

    public final NumberPath<Long> publishNum = createNumber("publishNum", Long.class);

    public final NumberPath<Long> revenue = createNumber("revenue", Long.class);

    public final StringPath stockCode = createString("stockCode");

    public final NumberPath<Long> totAsset = createNumber("totAsset", Long.class);

    public final NumberPath<Long> totDebt = createNumber("totDebt", Long.class);

    public QCorpInfoEntity(String variable) {
        super(CorpInfoEntity.class, forVariable(variable));
    }

    public QCorpInfoEntity(Path<? extends CorpInfoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorpInfoEntity(PathMetadata metadata) {
        super(CorpInfoEntity.class, metadata);
    }

}

