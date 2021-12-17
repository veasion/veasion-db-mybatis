package cn.veasion.db.mybatis;

import cn.veasion.db.jdbc.EntityDao;
import cn.veasion.db.jdbc.JdbcEntityDao;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * MybatisEntityDao
 *
 * @author luozhuowei
 * @date 2021/12/17
 */
public class MybatisEntityDao<T, ID> extends JdbcEntityDao<T, ID> {

    private DataSource dataSource;
    private Class<T> entityClass;
    private Class<?> mapperInterface;

    public Class<?> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public MybatisEntityDao<T, ID> setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        if (entityClass != null) {
            return entityClass;
        }
        Type[] genericInterfaces = mapperInterface.getGenericInterfaces();
        if (genericInterfaces == null || genericInterfaces.length == 0) {
            throw new RuntimeException("接口定义异常");
        }
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) genericInterface).getRawType();
                if (rawType instanceof Class && EntityDao.class.isAssignableFrom((Class<?>) rawType)) {
                    Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    return (entityClass = (Class<T>) actualTypeArguments[0]);
                }
            }
        }
        throw new RuntimeException("获取实体类型失败，请重写 getEntityClass() 方法");
    }

}
