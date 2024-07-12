package cn.gojava.app.common.typehandler;

import cn.gojava.app.common.utils.json.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {

    public abstract Class<T> getTypeClass();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JsonUtils.toJsonString(list));
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JsonUtils.toList(resultSet.getString(s), getTypeClass());
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JsonUtils.toList(resultSet.getString(i), getTypeClass());
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JsonUtils.toList(callableStatement.getString(i), getTypeClass());
    }
}