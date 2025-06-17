package com.fei.config;

import ch.qos.logback.core.db.DBHelper;
import com.fei.beans.Pet;
import com.fei.beans.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/*
  1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
  2、配置类本身也会被注册为组件
  3、proxyBeanMethods：代理bean的方法
       Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
       Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
       组件依赖必须使用Full模式默认。其他默认是否Lite模式

     结论：
       配置类组件之间无依赖关系用Lite模式加速容器启动过程，减少判断
       配置类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式

  4、@Import({User.class, DBHelper.class})
       给容器中自动创建出这两个类型的组件【调用这两个组件的无参构造】、默认组件的名字就是全类名【就是前面有包名】
 */
//@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = true)  // 告诉SpringBoot这是一个配置类 == 配置文件
//@ConditionalOnBean(name = "tom")
@ConditionalOnMissingBean(name = "tom")
@ImportResource("classpath:beans.xml")
public class MyConfig {

    /**
     * Full:外部无论对配置类中的这个组件的注册方法调用多少次获取的都是之前注册容器中的单实例对象
     */
    @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User user01(){
        User user = new User("zhangsan", 18);
        //user组件依赖了Pet组件
        user.setPet(pet()); // 这里调用pet()方法返回的是否是单例的也和上面的配置有关
        return user;
    }

    @Bean("tom22")
    public Pet pet(){
        return new Pet("tomcat");
    }
}
