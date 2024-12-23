package com.workflow.config.dbconfig;

import com.workflow.annotation.Master;
import com.workflow.annotation.Slave;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(master)")
    public void useMaster(Master master) {
        DatabaseContextHolder.setDatabaseType(DatabaseContextHolder.MASTER);
    }

    @Before("@annotation(slave)")
    public void useSlave(Slave slave) {
        DatabaseContextHolder.setDatabaseType(DatabaseContextHolder.SLAVE);
    }
}