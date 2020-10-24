package com.configs.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.publisher_service.model.Employee;
import com.publisher_service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.sql.DataSource;

public class DataSourceTest {
    private EmployeeRepository employeeRepository;

    @Autowired
    public DataSourceTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = null;
        new DataSourceTest(employeeRepository).testDataSource("mysql");
    }

    private void testDataSource(String dbType) {
        Optional<Employee> employee = employeeRepository.findById(1000);
        System.out.println(employee.get().getName());
        DataSource ds = null;
        if("mysql".equals(dbType)) {
            ds = new DatabaseConfig().mysqlDataSource();
        }else{
            System.out.println("invalid db type");
            return;
        }

        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/learning");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("gime@job1");
        ds = mysqlDS;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from employee");
            while(rs.next()){
                System.out.println("Employee ID="+rs.getInt("id")+", " +
                        "Name="+rs.getString("name")+", " +
                        "Employee="+rs.getString("employee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

//        PublisherRepository publisherRepository;
//        publisherRepository.save(employee);
    }

}

