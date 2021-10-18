package resource_pool.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.concurrent.CyclicBarrier;


@MapperScan("resource_pool.demo.mapper")
@SpringBootApplication
//@EnableScheduling   // 2.开启定时任务
public class DemoApplication {
    public static void main(String[] args) {
//        new Thread(){
//            @Override
//            public void run(){
//                try {
//                    sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                for(int i=0;i<10;i++){
//                    System.out.println(i);
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//        ProxyFactory
//        ObjenesisCglibAopProxy
//                CglibAopProxy
//        AopProxy
//        JdkDynamicAopProxy
//        InvocationHandler
//        DefaultAopProxyFactory
//        DefaultListableBeanFactory
//        AbstractBeanDefinition
//        BeanDefinition
//        MutablePropertySources b=new MutablePropertySources();
//        PropertySourcesPropertyResolver a=new PropertySourcesPropertyResolver(b);
//        a.resolveRequiredPlaceholders("");
//        new AbstractBeanDefinitionReader().loadBeanDefinitions(new EncodedResource());
//        ApplicationContext
//        ClassPathXmlApplicationContext container =new ClassPathXmlApplicationContext("spring.xml");
//        container.refreshBeanFactory();
//        container.loadBeanDefinitions(new DefaultListableBeanFactory());
//        container.loadBeanDefinitions();
//        AbstractRefreshableApplicationContext
////        XmlBeanFactory
//        container.getBean("1");
        SpringApplication.run(DemoApplication.class, args);
//        argsAnnotationConfigApplicationContext  applicationContext2 = new AnnotationConfigApplicationContext(MainConfig.class);
//        applicationContext2.getBean("Test");
    }

}

//@Component
//class Test{
//    Test2 t;
//}
//
//@Component
//class Test2{
//    Test t;
//}

//@Configuration
//class MainConfig {
//    @Bean()
//    public Test getTest(){
//        return new Test();
//    }
//    @Bean
//    public MyBeanPostProcessor getMyBeanPostProcessor(){
//        return new MyBeanPostProcessor();
//    }
//}
//
//class MyBeanPostProcessor implements BeanPostProcessor {
//    public Object postProcessBeforeInitialization(Object bean, String beanName)
//            throws BeansException {
//        // 这边只做简单打印   原样返回bean
//        System.out.println("postProcessBeforeInitialization====" + beanName);
//        return bean;
//    }
//
//    public Object postProcessAfterInitialization(Object bean, String beanName)
//            throws BeansException {
//        // 这边只做简单打印   原样返回bean
//        System.out.println("postProcessAfterInitialization====" + beanName);
//        return bean;
//    }
//}


