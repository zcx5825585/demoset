package zcx.jooqdemo;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import zcx.jooqdemo.model.tables.Book;

import java.sql.Connection;
import java.sql.DriverManager;

public class JooqDemo {
    public static void main(String[] args) {
        //用户名
        String userName = "root";
        //密码
        String password = "sa123";
        //mysql链接url
        String url = "jdbc:mysql://localhost:3306/jooqdb?serverTimezone=UTC&amp&characterEncoding=utf-8";
        Connection conn;
        try {
            //这是JDBC Mysql连接
            conn = DriverManager.getConnection(url, userName, password);
            //基于JOOQ实现的简单查询
            //传入Connection连接对象、数据方言得到一个DSLContext的实例，然后使用DSL对象查询得到一个Result对象。
            DSLContext using = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> fetch = using.select().from(Book.BOOK).fetch();


            //for循环输出结果
            for (Record record : fetch) {
                Integer id = record.getValue(Book.BOOK.ID);
                String name = record.getValue(Book.BOOK.NAME);
                /**
                 * 控制台输出
                 * ID: 1 first name: 3 last name: zhang
                 * ID: 2 first name: 4 last name: li
                 */
                System.out.println("ID:" + id + "  name:" + name);
            }
            //关闭连接
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
