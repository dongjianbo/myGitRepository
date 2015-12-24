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
	public static SqlSession getSqlSession(){
		if(session==null){
			try {
				Reader reader=Resources.getResourceAsReader(resource);
				SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
				SqlSessionFactory factory=builder.build(reader);
				session=factory.openSession();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return session;
	}
	public static void main(String[] args) {
		System.out.println(BaseSessionFactory.getSqlSession());
	}
}
