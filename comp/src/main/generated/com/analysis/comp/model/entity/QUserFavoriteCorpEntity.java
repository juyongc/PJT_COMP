package com.analysis.comp.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFavoriteCorpEntity is a Querydsl query type for UserFavoriteCorpEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserFavoriteCorpEntity extends EntityPathBase<UserFavoriteCorpEntity> {

    private static final long serialVersionUID = 1684789472L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFavoriteCorpEntity userFavoriteCorpEntity = new QUserFavoriteCorpEntity("userFavoriteCorpEntity");

    public final QCorpInfoEntity corpInfo;

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUserEntity user;

    public QUserFavoriteCorpEntity(String variable) {
        this(UserFavoriteCorpEntity.class, forVariable(variable), INITS);
    }

    public QUserFavoriteCorpEntity(Path<? extends UserFavoriteCorpEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFavoriteCorpEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFavoriteCorpEntity(PathMetadata metadata, PathInits inits) {
        this(UserFavoriteCorpEntity.class, metadata, inits);
    }

    public QUserFavoriteCorpEntity(Class<? extends UserFavoriteCorpEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.corpInfo = inits.isInitialized("corpInfo") ? new QCorpInfoEntity(forProperty("corpInfo")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

