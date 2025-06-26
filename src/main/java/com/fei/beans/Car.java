package com.fei.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 只有在容器中的组件，才会拥有SpringBoot提供的强大能力
 * <p>
 * 说明：
 * 1. @ConfigurationProperties(prefix = "mycar") 的作用：
 *    这个注解会将配置文件（如 application.yml）中以 mycar 开头的属性，自动绑定到本类的同名属性上。
 *    例如 mycar.brand=xxx 会自动注入到 brand 属性。
 * 2. 必须配合主类上的 @EnableConfigurationProperties(Car.class) 一起用，
 *    才能让本类被 Spring 容器管理并实现属性注入。
 * 3. 这样就可以通过 @Autowired 注入 Car，且属性值自动填充。
 */
//@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "mycar")
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private String brand;
    private Integer price;

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
