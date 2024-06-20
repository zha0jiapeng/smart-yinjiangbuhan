package com.ruoyi.assessment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class RuoyiAssessmentApplication /*implements WebMvcConfigurer*/ {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(RuoyiAssessmentApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");

		// 未配置默认空白
		if(path == null){
			path = "";
		}


		log.info("\n----------------------------------------------------------\n\t" +
				"云帆考试系统启动成功，访问路径如下:\n\t" +
				"本地路径: \t\thttp://localhost:" + port + path + "/\n\t" +
				"网络地址: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"API文档: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

/*	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		//保留原有converter,把新增fastConverter插入集合头,保证优先级
		converters.add(0, JsonConverter.fastConverter());
	}*/

}
