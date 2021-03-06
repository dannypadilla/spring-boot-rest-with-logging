
package sbrest.web.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbrest.model.Employee;
import sbrest.model.EmployeeDto;
import sbrest.model.dao.EmployeeDao;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
	
	/* Logger init here */
	private static Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping
    public List<EmployeeDto> list()
    {
        List<Employee> employees = employeeDao.getEmployees();
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        for( Employee employee : employees ) {
            employeeDtos.add( new EmployeeDto( employee ) );
        }
        
        logger.warn("a WARNING from list()");
        logger.info("some INFO from list()");
        logger.error("an ERROR from list()");
        logger.debug("   DEBUG from list()");
        logger.trace("A TRACE  from list()");
        
        return employeeDtos;
    }

    
    @GetMapping("/{id}")
    public EmployeeDto get( @PathVariable Integer id )
    {
//    	logger.warn(id.toString());
    	Employee employee = employeeDao.getEmployee( id );
    	logger.trace("Trying to find Employee with ID " + id + " from get()");
    	
        if ( employee == null ) {
        	logger.error("Employee with ID " + id + " was not found in get()");
            throw new ResponseStatusException( HttpStatus.NOT_FOUND,
            		"Employee not found");
        }
       
        logger.info("Pring the Employee's information (INFO-level)");
        logger.info(employee.getName() + " , " + employee.getId() );
        
        logger.debug("Pring the Employee's information (DEBUG-level)");
        logger.debug(employee.getName() + " , " + employee.getId() );
        
        logger.trace("Pring the Employee's information (trace-level)");
        logger.trace(employee.getName() + " , " + employee.getId() );
        
        return new EmployeeDto( employee );
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto add( @RequestBody EmployeeDto employeeDto )
    {
        if( !StringUtils.hasText( employeeDto.getName() ) ) {
        	throw new ResponseStatusException( HttpStatus.BAD_REQUEST,
        			"Employee name is required" );        	
        }

        Employee employee = new Employee();
        employee.setName( employeeDto.getName() );
        if( employeeDto.getSupervisorId() != null )
        {
            Employee supervisor = employeeDao
                .getEmployee( employeeDto.getSupervisorId() );
            employee.setSupervisor( supervisor );
        }

        employee = employeeDao.saveEmployee( employee );
        return new EmployeeDto( employee );
    }

}