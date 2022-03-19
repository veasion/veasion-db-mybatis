package cn.veasion.db.mybatis;

import cn.veasion.db.DbException;
import cn.veasion.db.jdbc.EntityDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * EntityDaoProxy
 *
 * @author luozhuowei
 * @date 2021/12/17
 */
public class EntityDaoProxy implements InvocationHandler {

    private Object object;
    private EntityDao<?, ?> entityDao;

    public EntityDaoProxy(Object object, EntityDao<?, ?> entityDao) {
        this.object = object;
        this.entityDao = entityDao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (EntityDao.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(entityDao, args);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof DbException) {
                    throw targetException;
                } else {
                    throw e;
                }
            }
        } else {
            return method.invoke(object, args);
        }
    }

}
