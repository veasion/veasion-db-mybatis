package cn.veasion.db.mybatis;

import cn.veasion.db.jdbc.EntityDao;
import org.mybatis.spring.mapper.MapperFactoryBean;

import java.lang.reflect.Proxy;

/**
 * MybatisMapperFactoryBean
 *
 * @author luozhuowei
 * @date 2021/12/17
 */
public class MybatisMapperFactoryBean<T> extends MapperFactoryBean<T> {

    public MybatisMapperFactoryBean() {
        super();
    }

    public MybatisMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        T object = super.getObject();
        Class<T> mapperInterface = getMapperInterface();
        if (EntityDao.class.isAssignableFrom(mapperInterface)) {
            MybatisEntityDao<?, ?> mybatisEntityDao = new MybatisEntityDao<>();
            mybatisEntityDao.setMapperInterface(mapperInterface);
            mybatisEntityDao.setDataSource(getSqlSession().getConfiguration().getEnvironment().getDataSource());
            EntityDaoProxy entityDaoProxy = new EntityDaoProxy(object, mybatisEntityDao);
            return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, entityDaoProxy);
        } else {
            return object;
        }
    }

}
