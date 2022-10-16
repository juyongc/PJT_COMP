package com.analysis.comp.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -650195878L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath email = createString("email");

    public final ListPath<UserFavoriteCorpEntity, QUserFavoriteCorpEntity> favoriteCorpList = this.<UserFavoriteCorpEntity, QUserFavoriteCorpEntity>createList("favoriteCorpList", UserFavoriteCorpEntity.class, QUserFavoriteCorpEntity.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath pw = createString("pw");

    public final NumberPath<Long> userpk = createNumber("userpk", Long.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

