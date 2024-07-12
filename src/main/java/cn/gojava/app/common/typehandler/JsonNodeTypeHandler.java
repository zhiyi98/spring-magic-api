package cn.gojava.app.common.typehandler;

import cn.gojava.app.common.utils.json.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.JAVA_OBJECT})
@MappedTypes({JsonNode.class})
public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType)
            throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        try {
            jsonObject.setValue(parameter.toString());
            ps.setObject(i, jsonObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonSource = rs.getString(columnName);
        if (jsonSource != null) {
            try {
                return JsonUtils.toJsonNode(jsonSource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonSource = rs.getString(columnIndex);
        if (jsonSource != null) {
            try {
                return JsonUtils.toJsonNode(jsonSource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonSource = cs.getString(columnIndex);
        if (jsonSource != null) {
            try {
                return JsonUtils.toJsonNode(jsonSource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
