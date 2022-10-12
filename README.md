# veasion-db-mybatis

veasion-db-mybatis 是一个用来适配 spring-mybatis、[veasion-db](https://github.com/veasion/veasion-db) 的扩展工程,
它支持 [veasion-db](https://github.com/veasion/veasion-db) 和 mybatis 在 springboot / spring 中的适配。

## maven 依赖
```xml
<!-- veasion-db -->
<dependency>
    <groupId>cn.veasion</groupId>
    <artifactId>veasion-db</artifactId>
    <version>1.1.7</version>
</dependency>

<!-- veasion-db-mybatis -->
<dependency>
    <groupId>cn.veasion</groupId>
    <artifactId>veasion-db-mybatis</artifactId>
    <version>1.0.4</version>
</dependency>
```
## 使用方式介绍

### springboot 集成
```
// application 启动类上加入 @Import 注解

@Import(cn.veasion.db.spring.MybatisBeanDefinitionRegistryPostProcessor.class)
```

### spring 集成
```
// mapper扫描注解的 factoryBean 指定使用 cn.veasion.db.mybatis.MybatisMapperFactoryBean.class

@MapperScan(..., factoryBean = cn.veasion.db.mybatis.MybatisMapperFactoryBean.class)
```

### mapper
```java
// mybatis mapper 接口继承 cn.veasion.db.jdbc.EntityDao<T, ID>

public interface UserMapper extends EntityDao<UserPO, Long> {
    // 其他代码会走 mybatis...
}
```

### 扩展 dataSource
正常情况下不需要扩展，默认走 mybatis sqlSession 中的数据源。
```
// 如有特殊需求可通过自定义方法去实现走不同的 dataSource
cn.veasion.db.spring.DefaultDataSourceProvider.setDataSourceProvider((entityDao, jdbcTypeEnum) -> {
    // 根据 jdbcTypeEnum 判断走不同 数据源
    return SpringUtils.getBean(DataSource.class);
});
```

## 赞助

项目的发展离不开您的支持，请作者喝杯咖啡吧~

ps：辣条也行 ☕

![支付宝](https://veasion.oss-cn-shanghai.aliyuncs.com/alipay.png?x-oss-process=image/resize,m_lfit,h_360,w_360)
