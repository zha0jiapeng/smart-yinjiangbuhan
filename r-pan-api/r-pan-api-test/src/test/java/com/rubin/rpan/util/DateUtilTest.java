package com.rubin.rpan.util;

import com.rubin.rpan.launch.RPanLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 日期工具测试类
 */
@SpringBootTest(classes = RPanLaunch.class)
@RunWith(SpringRunner.class)
public class DateUtilTest {

    @Test
    public void getIdTest() {
        System.out.println(DateUtil.getYear());
        System.out.println(DateUtil.getMonth());
        System.out.println(DateUtil.getDay());
    }

}
