mysql-builder
=============

a api for java that allow you builder sql string use builder class and methods

example:

		SQLBuilder sqlBuilder = new SQLBuilder(new SimplePerformBuilder("SELECT FROM person"));
		sqlBuilder.where().createCriteria().equalTo("name", "lily").greaterThan("age", 45).notIn("group", 12,23,45);
		sqlBuilder.where().or().like("name", "j%k").notBetween("age", 34, 67);
		String sqlString = sqlBuilder.createSQL();
		System.out.println(sqlString);
		List<Object> paramsList = sqlBuilder.getParams();
		
the above code will print：
SELECT FROM person WHERE (name = ? AND age > ? AND `group` NOT IN (?,?,?)) OR (name LIKE ? AND age NOT BETWEEN ? AND ?)

then you can use this sql string with spring jdbc or jdbc.
		
		