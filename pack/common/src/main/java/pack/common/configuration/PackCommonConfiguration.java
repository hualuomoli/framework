package pack.common.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 指定项目的扫码包,用于与其它目录融合. 否则当前包不被spring管理
@Configuration(value = "pack.common.configuration.PackCommonConfiguration")
@ComponentScan(value = "pack.common")
public class PackCommonConfiguration {
}
