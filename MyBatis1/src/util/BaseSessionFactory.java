package util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BaseSessionFactory {
	public static String resource="mybatis_cfg.xml";
	public static SqlSession session=null;
	private final static SqlSessionFactory factory;
	static{
		Reader reader=null;
		try {
			reader=Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
		factory=builder.build(reader);
	}
	public static SqlSession getSqlSession(){
		if(session==null){
			
				
				session=factory.openSession();
			
		}
		return session;
	}
	public static SqlSessionFactory getSqlSessionFactory(){
		return factory;
	}
	public static void main(String[] args) {
		System.out.println(BaseSessionFactory.getSqlSession());
	}
}
