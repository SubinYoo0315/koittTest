package jdbc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

//DB 커넥션 풀을 준비하기 위한 컨텍스트 리스너 클래스!
public class DBCPInitListener implements ServletContextListener{

	//시작할 때 우리의 DB커넥션 풀을 셋팅하도록 해보자!
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//서블릿 컨텍스트를 받고
		ServletContext sc = sce.getServletContext();
		//서블릿 컨텍스트를 통해서 init파라미터를 받음
		//파일주소로 파일을 읽어야함! 시스템상 주소!
		String poolConfigFile = sc.getRealPath(sc.getInitParameter("poolConfigFile"));
		
		//파일주소로 프로퍼티스 객체에 파일에 있는 데이타를 넣을 것임!
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(poolConfigFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("not found poolConfigFile", e);
		} catch (IOException e) {
			throw new RuntimeException("fail to read poolConfigFile", e);
		}
		//jdbc드라이버 로드!
		loadJDBCDriver(prop);
		//커넥션 풀 초기화!
		initConnectionPool(prop);
	}
	
	private void initConnectionPool(Properties prop) {
		try {
			String uri = prop.getProperty("jdbcUrI");
			String user = prop.getProperty("dbUser");
			String password = prop.getProperty("dbPwd");
			// 커넥션 풀이 새로운 커넥션을 생성할 때 사용할 커넥션 팩토리 생성
			ConnectionFactory connFactory = new DriverManagerConnectionFactory(uri, user, password);

			// PoolableConnection 을 생성하는 팩토리 생성. DBCP는 커넥션 풀에 커넥션을 보관할 때
			// PoolableConnection을 사용. 내부적으로는 실제 커넥션을 담고 있음.
			// 커넥션을 close하면 실제로 닫지않고 반환.
			// 풀에서 쓸수있는 커넥션을 만들어주는 팩토리에 커넥션 팩토리를 넣고 생성
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connFactory, null);

			// 커넥션이 유효한지 확인하기 위한 쿼리를 지정
			// getProperty의 첫번째 인자는 파일에 정의되어있는 값이고, 두번째 인자는 없을시! 기본값으로 해줄 값!
			poolableConnectionFactory.setValidationQuery(prop.getProperty("validationQuery", "select 1"));

			// 커넥션 풀의 설정 정보를 생성하고 설정정보를 셋팅한다.
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig<>();

			// 커넥션 풀의 설정정보를 가지고 검사주기 셋팅
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);

			// 풀에 보관중인 커넥션이 유효한지 검사여부
			poolConfig.setTestWhileIdle(true);
			
			// 커넥션 최소 개수
			poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("minIdle", "5")));
			
			// 커넥션 최대 개수
			poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal", "50")));

			// 커넥션 풀 생성시 팩토리와 커넥션 설정을 받음!
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory,
					poolConfig);
			poolableConnectionFactory.setPool(connectionPool);
			Class.forName(prop.getProperty("poolingDriver"));
			System.out.println("jdbc 드라이버 등록 성공");
			String poolName = prop.getProperty("poolName");
			// 커넥션 풀 드라이버에 생성한 커넥션 풀을 등록!
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool(poolName, connectionPool);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Not found poolingDriver Class", e);
		} catch (SQLException e) {
			throw new RuntimeException("can not get Driver PoolingDriver",e);
		}
	}
	
	private void loadJDBCDriver(Properties prop) {
		//프로퍼티에서 설정한 드라이버 주소로 클래스 로드
		String driverClass = prop.getProperty("jdbcDriver");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load JDBC Driver", e);
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
