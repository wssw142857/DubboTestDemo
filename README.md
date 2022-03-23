# Springboot-Dubbo



> #### Java

```cmd
java version "1.8.0_321"
Java(TM) SE Runtime Environment (build 1.8.0_321-b07)
Java HotSpot(TM) 64-Bit Server VM (build 25.321-b07, mixed mode)
```



> #### Maven依赖环境

```xml
<!--dubbo-->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>2.7.8</version>
</dependency>
<!--curator-->
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>5.1.0</version>
</dependency>
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>5.1.0</version>
</dependency>
```



> #### 当前dubbo应用服务

```yml
dubbo:
  application:
    name: user-service-provider
```



> #### Registry注册中心配置

```yml
dubbo:
  registry:
    address: 127.0.0.1:2181	#注册中心地址
    protocol: zookeeper		#注册中心协议
```



> #### Monitor监控中心配置

```yml
dubbo:
  monitor:
    protocol: registry		#去注册中心自动发现
```



> #### 服务提供者协议配置

```yml
dubbo:
  protocol:
    name: dubbo
    port: 20881
```



> #### 服务提供者暴露服务

1. 在主类上添加`@EnableDubbo`注解开启基于注解的dubbo功能
2. 在要暴露的服务实现类上标注`@DubboService`注解, 即可暴露服务



> #### 服务消费方消费服务

1. 在主类上添加`@EnableDubbo`注解开启基于注解的dubbo功能
2. 在调用服务时, 使用`@DubboReference`注解代替`@Autowired`自动注入



> #### 服务版本控制

​	实现灰度发布, 协助新旧版本的稳定更迭

1. 在对应服务注解的`@DubboService(version="0.0.0")`中确定版本号
2. 在对应消费者引用注解的`@DubboReference(version="0.0.0")`中引用对应的版本, 如果引用的版本号为"*", 则将随机引用版本



> #### 本地存根

​	类似于拦截器, 在服务调用前进行一些额外操作

1. 例

```java
public class UserServiceStub implements UserService {

    private final UserService userService;

    public UserServiceStub(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getUserMsg() {
        return userService.getUserMsg();
    }
}
```

2. 调用服务时, 在注解`@DubboReference(stub="this")`中调用stub, 如果值为this, 则要将其置于对应接口同级目录, 且在服务接口名的基础上加上后缀Stub, 否则, 值则为全类名



> #### 负载均衡机制

​	在集群模式下, 为了减少单一服务器上的压力, 会将请求分配到不同服务器

1. 负载均衡的策略
   - Random LoadBalance 	         基于权重的随机负载均衡机制
   - RoundRobin LoadBalance       基于权重的轮询负载均衡机制
   - LeastActive LoadBalance         最少活跃数负载均衡机制
   - ConsistentHash LoadBalance 一致性hash负载均衡机制
2. 在调用服务时在注解`@DubboReference(loadbalance="random")`中指定负载均衡机制, 默认为随机负载均衡



> #### 服务容错

1. 但集群调用失败时的多种容错方案

   - Failover Cluster 

     失败自动切换, 重试其他服务器, 通常用于读操作, 但重试会带来更长的延迟, 可以通过设置retries="2"来设置重试次数

   - Failfast Cluster

     快速失败, 只发起一次调用, 失败立即报错, 通常用于非幂等的写操作, 比如新增记录

   - Failsafe Cluster

     失败安全, 出现异常直接忽略, 但会写入审计日志中.

   - Failback Cluster

     失败自动恢复, 后台记录失败请求, 定时重发, 通常用于消息通知等必需成功的操作

   - ForKing Cluster

     并行调用多个服务器, 只要一个成功既可返回. 通常用于实时性较高的读操作, 但浪费更多服务资源, 可通过设置forks="2"设置最大并行数

   - Broading Cluster

     广播调用所有提供者, 逐个调用, 任意一台报错则报错, 通常用于通知所有提供者更新缓存或者日志等本地资源.

2. 配置服务容错在注解`@DubboReference(cluster="failsafe")`中指定.



> #### 常用配置

``` yml
dubbo:
  config-center:
    timeout: 30000			#获取配置的超时时间, 默认为3000
    
  registry:
    timeout: 30000			#注册中心请求超时时间 (毫秒), 默认为5000
  	
  consumer:
    check: false			#启动时检查提供者是否存在，true 报错，false 忽略, 默认为true
    timeout: 30000			#远程服务调用超时时间 (毫秒), 默认为1000
  	retries: 3				#远程服务调用重试次数，不包括第一次调用，不需要重试请设为 0, 默认为2
```



> #### 官方文档

[dubbo官方文档](https://dubbo.apache.org/zh/)

