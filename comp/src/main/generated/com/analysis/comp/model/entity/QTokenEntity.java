package com.analysis.comp.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTokenEntity is a Querydsl query type for TokenEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTokenEntity extends EntityPathBase<TokenEntity> {

    private static final long serialVersionUID = 1314172912L;

    public static final QTokenEntity tokenEntity = new QTokenEntity("tokenEntity");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Long> tokenPk = createNumber("tokenPk", Long.class);

    public QTokenEntity(String variable) {
        super(TokenEntity.class, forVariable(variable));
    }

    public QTokenEntity(Path<? extends TokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTokenEntity(PathMetadata metadata) {
        super(TokenEntity.class, metadata);
    }

}

