package com.wbsrisktaskerx.wbsrisktaskerx.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wbsrisktaskerx.wbsrisktaskerx.entity.QAdmin.admin;

@Component
public class AdminQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public AdminQuerydslRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Admin> getAll(){
        return jpaQueryFactory.selectFrom(admin).fetch();
    }
}