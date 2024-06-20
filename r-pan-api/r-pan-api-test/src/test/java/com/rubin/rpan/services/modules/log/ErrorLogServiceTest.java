package com.rubin.rpan.services.modules.log;

import com.rubin.rpan.launch.RPanLaunch;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统错误日志测试类
 */
@SpringBootTest(classes = RPanLaunch.class)
@RunWith(SpringRunner.class)
@Transactional
public class ErrorLogServiceTest {

    @Autowired
    @Qualifier(value = "errorLogService")
    private IErrorLogService iErrorLogService;

    /**
     * 测试保存成功
     * PS:由于系统不严格要求日志必须保存成功，故只测试该方法执行无报错即可
     */
    @Test
    @Rollback
    public void saveSuccessTest() {
        iErrorLogService.save("测试日志内容", 0L);
    }

}
