package com.yc.mpmvcboot.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MpMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.........");
        this.strictInsertFill(metaObject,"insertTime", Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill.........");
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }
}
