package cn.veasion.db.spring;

import cn.veasion.db.base.JdbcTypeEnum;
import cn.veasion.db.jdbc.DataSourceProvider;
import cn.veasion.db.jdbc.EntityDao;
import cn.veasion.db.mybatis.MybatisEntityDao;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.BiFunction;

/**
 * DefaultDataSourceProvider
 *
 * @author luozhuowei
 * @date 2021/12/17
 */
public class DefaultDataSourceProvider implements DataSourceProvider {

    private static BiFunction<EntityDao<?, ?>, JdbcTypeEnum, DataSource> DATA_SOURCE_BI_FUNCTION;

    /**
     * 提供 dataSource 扩展
     */
    public static void setDataSourceProvider(BiFunction<EntityDao<?, ?>, JdbcTypeEnum, DataSource> function) {
        DATA_SOURCE_BI_FUNCTION = function;
    }

    @Override
    public DataSource getDataSource(EntityDao<?, ?> entityDao, JdbcTypeEnum jdbcTypeEnum) {
        DataSource dataSource = null;
        if (DATA_SOURCE_BI_FUNCTION != null) {
            dataSource = DATA_SOURCE_BI_FUNCTION.apply(entityDao, jdbcTypeEnum);
        }
        if (dataSource == null && entityDao instanceof MybatisEntityDao) {
            dataSource = ((MybatisEntityDao<?, ?>) entityDao).getDataSource();
        }
        return dataSource;
    }

    @Override
    public Connection getConnection(DataSource dataSource) throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public void releaseConnection(DataSource dataSource, Connection connection) {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

}
