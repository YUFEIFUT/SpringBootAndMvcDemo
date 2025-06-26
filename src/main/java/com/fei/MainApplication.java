package com.fei;

import com.fei.beans.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 主程序类
 * <p>
 * 说明：
 * 1. @EnableConfigurationProperties(Car.class) 的作用：
 *    这个注解会让 Spring Boot 启用 Car 这个配置属性类，并把它注册为一个 Bean。
 *    这样 Car 类就能被 Spring 容器管理，并且可以自动注入。
 * 2. 配合 Car 类上的 @ConfigurationProperties(prefix = "mycar")，
 *    可以实现将配置文件（如 application.yml）中以 mycar 开头的属性自动绑定到 Car 对象的属性上。
 * 3. 这样你就可以在其他地方通过 @Autowired 直接注入 Car，属性值也会自动填充。
 */
@SpringBootApplication
@EnableConfigurationProperties(Car.class)
public class MainApplication {

    public static void main(String[] args) {
        // 1、返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);

        // 2、查看容器里面的组件
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name);
//        }
//
//        // 3、从容器中获取组件
//        Pet tom01 = run.getBean("tom", Pet.class);
//
//        Pet tom02 = run.getBean("tom", Pet.class);
//
//        System.out.println("组件："+(tom01 == tom02)); // true
//
//        //4、com.atguigu.boot.config.MyConfig$$EnhancerBySpringCGLIB$$51f1e1ca@1654a892
//            // 这个配置类被注册实际上是被Spring CGLIB动态代理增强了的【如果proxyBeanMethods = true的话】
//            // 但是如果：proxyBeanMethods = false 的话，我们获取的bean就会是：
//        MyConfig bean = run.getBean(MyConfig.class);
//        System.out.println(bean);
//
//        //如果@Configuration(proxyBeanMethods = true)的话，此时代理对象调用方法，pringBoot总会检查这个组件是否在容器中有。
//        //保持组件单实例
//        User user = bean.user01();
//        User user1 = bean.user01();
//        System.out.println(user == user1); // true
//
//        User user01 = run.getBean("user01", User.class);
//        Pet tom = run.getBean("tom", Pet.class);
//
//        System.out.println("用户的宠物是不是容器中的宠物："+(user01.getPet() == tom));
//
//        // 5、获取组件
//        System.out.println("+++++++++++++");
//        String[] beanNamesForType = run.getBeanNamesForType(User.class);
//        for (String s : beanNamesForType) {
//            System.out.println(s);
//        }
//
//        DBHelper dbHelper = run.getBean(DBHelper.class);
//        System.out.println(dbHelper);

        // 测试 @Conditional
//        boolean tom = run.containsBean("tom");
//        System.out.println("容器中是否存在Tom组件："+tom);
//
//        boolean user01 = run.containsBean("user01");
//        System.out.println("容器中是否存在user01组件："+user01);
//
//        boolean tom22 = run.containsBean("tom22");
//        System.out.println("容器中是否存在tom22组件："+tom22);

        // 测试 @ImportResource
        boolean haha = run.containsBean("haha");
        boolean hehe = run.containsBean("hehe");
        System.out.println("haha："+haha);//true
        System.out.println("hehe："+hehe);//true

        System.out.println(run.getBean(Car.class));
    }
}