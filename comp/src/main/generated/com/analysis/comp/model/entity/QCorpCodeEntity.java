package com.analysis.comp.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorpCodeEntity is a Querydsl query type for CorpCodeEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCorpCodeEntity extends EntityPathBase<CorpCodeEntity> {

    private static final long serialVersionUID = -235788730L;

    public static final QCorpCodeEntity corpCodeEntity = new QCorpCodeEntity("corpCodeEntity");

    public final StringPath corpCode = createString("corpCode");

    public final StringPath corpName = createString("corpName");

    public final StringPath stockCode = createString("stockCode");

    public QCorpCodeEntity(String variable) {
        super(CorpCodeEntity.class, forVariable(variable));
    }

    public QCorpCodeEntity(Path<? extends CorpCodeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorpCodeEntity(PathMetadata metadata) {
        super(CorpCodeEntity.class, metadata);
    }

}

