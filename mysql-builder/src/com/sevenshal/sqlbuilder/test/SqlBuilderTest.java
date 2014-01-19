package com.sevenshal.sqlbuilder.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.sevenshal.sql.SQLBuilder;
import com.sevenshal.sql.builder.perform.SimplePerformBuilder;

public class SqlBuilderTest {

	@Test
	public void test() {
		SQLBuilder sqlBuilder = new SQLBuilder(new SimplePerformBuilder("SELECT FROM person"));
		sqlBuilder.where().createCriteria().equalTo("name", "lily").greaterThan("age", 45).notIn("group", 12,23,45);
		sqlBuilder.where().or().like("name", "j%k").notBetween("age", 34, 67);
		String sqlString = sqlBuilder.createSQL();
		System.out.println(sqlString);
		List<Object> paramsList = sqlBuilder.getParams();
	}

}
