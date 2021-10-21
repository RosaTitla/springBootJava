package mx.edu.utp.semaforo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class mySqlConfig {
    private String driverClassName="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/";
    private String username="root";
    private String password="";
    private String schema="semaforoDB";
    private int poolSize=2;
    private String testQuery="SELECT 1";

    @Bean
    public DataSource getDataSource(){
        //https://www.youtube.com/watch?v=bM_Ianqpxj4



        final HikariDataSource dataSource=new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setSchema(schema);
        dataSource.setMaximumPoolSize(poolSize);
        dataSource.setPoolName(schema);
        dataSource.setConnectionTestQuery(testQuery);
        return dataSource;
    }

}
