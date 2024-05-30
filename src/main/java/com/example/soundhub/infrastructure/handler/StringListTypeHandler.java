package com.example.soundhub.infrastructure.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
		// 리스트를 콤마로 구분된 하나의 문자열로 변환
		String joined = String.join(",", parameter);
		ps.setString(i, joined);
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		return value != null ? Arrays.asList(value.split(",")) : null;
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return value != null ? Arrays.asList(value.split(",")) : null;
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return value != null ? Arrays.asList(value.split(",")) : null;
	}
}
