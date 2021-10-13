package sbrest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbrest.web.controller.EmployeesController;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(Employee.class);    

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    public Employee() {
    	//logger.trace("Hello from Employees");
    }

    @ManyToOne
    private Employee supervisor;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
    	//logger.trace("Hello from Employees.getName() ");
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Employee getSupervisor()
    {
        return supervisor;
    }

    public void setSupervisor( Employee supervisor )
    {
        this.supervisor = supervisor;
    }

}
