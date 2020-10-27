package com.fly;

import com.fly.entity.Student;
import com.fly.mapper.StudentMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;


/**
 * 基于xml形式构建SqlSessionFactory
 */

public class Test01 {

    private  static SqlSessionFactory sqlSessionFactory;
    private static  SqlSession  sqlSession;


    /*通过xml构建sqlSessionFactory*/
    //@BeforeClass表示针对所有测试,只执行一次,且必须为static void
    @BeforeClass
    public static  void init(){
        try{
            InputStream stream= Resources.getResourceAsStream("mybatis.xml");

            //SqlSessionFactoryBuilder通过获取配置文件信息得到SqlSessionFactory,看到build()就想到了建造者模式
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(stream);
            sqlSession=sqlSessionFactory.openSession();
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*通过编写java配置构建sqlSessionFactory*/
    @Test
    public  void initConfig(){

        DataSource  dataSource=new PooledDataSource("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowMultiQueries=true",
                "root","root");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        //设置Mapper
        configuration.addMapper(StudentMapper.class);

        sqlSessionFactory=new SqlSessionFactoryBuilder().build(configuration);

        sqlSession=sqlSessionFactory.openSession();

        /*
        * 如果出现Cause: java.lang.IllegalArgumentException: Mapped Statements collection does not
        * contain value for com.fly.mapper.StudentMapper.queryStudentById 异常是idea的原因,idea默认不是编译java文件夹下的.xml文件,
        * 如果想测试的话,直接把studentMapper.xml复制到target/classes/com/fly/mapper目录下即可
        * */
        System.out.println(sqlSession.selectList("com.fly.mapper.StudentMapper.queryStudentById",4));
    }



    @Test
    public void addStudent(){
        Student student=new Student();
        student.setStuName("张三");
        student.setStuNo("s10001");
        student.setAge(20);
        student.setSex(0);
        student.setAddress("天空城");
        student.setBirthday("2020-01-01");
        //表示调用Mapper XML文件中insert标签的id为addStudent的语句并传入student参数执行
        int row=sqlSession.insert("addStudent",student);
        //提交成功数据库才有记录
        sqlSession.commit();
        System.out.println(row>0?"添加成功!":"添加失败!");
        Test01.close();
    }

    @Test
    public void delStudentById(){
        int row = sqlSession.delete("delStudentById", 9);
        //提交成功数据库才有记录
        sqlSession.commit();
        System.out.println(row>0?"删除成功!":"删除失败!");
        Test01.close();
    }

    @Test
    public void upStudent(){
        Student student=new Student();
        student.setId(1).setStuName("mybatis");
        int row = sqlSession.update("upStudent", student);
        //提交成功数据库才有记录
        sqlSession.commit();
        System.out.println(row>0?"修改成功!":"修改失败!");
        Test01.close();

    }
    @Test
    public void queryStudentById(){
        Student student = (Student)sqlSession.selectOne("com.fly.mapper.StudentMapper.queryStudentById", 4);
        System.out.println(student);
        Test01.close();
    }

    @Test
    public void queryStudent(){
        //selectList中填的是mapper中的方法
        List<Object> selectList = sqlSession.selectList("com.fly.mapper.StudentMapper.queryStudent");
        Test01.close();
    }

    public static  void close(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }
}
