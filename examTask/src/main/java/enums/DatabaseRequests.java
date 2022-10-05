package enums;

public class DatabaseRequests {

    private DatabaseRequests() {}

    public static final String SQL_GET_TESTS = "SELECT * FROM test WHERE (test.name, test.start_time) IN (SELECT DISTINCT" +
            " test.name, MAX(test.start_time) FROM test JOIN project ON project.id = test.project_id WHERE project.name='%s'" +
            " GROUP BY test.name) order by test.start_time desc limit %d";
    public static final String INSERT_IMG_ATTACH_QUERY = "INSERT INTO attachment (content,content_type,test_id) VALUES (?,?,?)";
    public static final String INSERT_LOG_QUERY = "INSERT INTO log (content,is_exception,test_id) VALUES (?,?,?)";
    public static final String INSERT_TEST_QUERY = "INSERT INTO test (name,method_name,start_time,end_time) VALUES (%s,%s,%s,%s)";
    public static final String SELECT_ADDED_TEST = "SELECT * FROM test WHERE name='%s' and method_name=%s and start_time=%s and end_time=%s";
}