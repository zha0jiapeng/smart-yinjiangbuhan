package com.ruoyi.framework.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("fast_dfs.properties")
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
public class FdfsConfiguration {
}
