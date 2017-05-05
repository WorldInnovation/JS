package com.aimprosoft.controller;


import com.aimprosoft.exeption.DaoExp;
import com.aimprosoft.exeption.ValidateExp;
import com.aimprosoft.model.Department;
import com.aimprosoft.model.Employee;
import com.aimprosoft.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EmployeesController extends ExceptionHandlingController {
    @Autowired
    private EmployeeService employeeService;

    @InitBinder()
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/employeesList", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> editDepartment(Long depID) throws DaoExp {
        Department department = null;
        List<Employee> employees = employeeService.listEmployee(depID);
       // if (depID != null) department = departmentService.getDepartmentById(depID);
        return employees;
    }

/*    @RequestMapping(value = "/employeesList", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployees( Long depID) throws DaoExp {
        //List<Employee> employees = null;
        if (0 != depID) {
           // employees = employeeService.listEmployee(depID);
        }
        return "test";
    }*/

    @RequestMapping(value = "/employeeEdit", method = RequestMethod.GET)
    @ResponseBody
    public String employeeEdit( Long depID, Long empID, Model model) throws DaoExp {
        model.addAttribute("depID", depID);
        if (null != empID) {
            Employee employee = employeeService.getEmpByID(empID);
            model.addAttribute("empID", employee.getId());
            model.addAttribute("employee", employee);
        }
        return "empEdit";
    }

    @RequestMapping(value = "/empDelete", method = RequestMethod.POST)
    public String empDelete(@RequestParam("depID") Long depID, @RequestParam("empID") Long empID) throws DaoExp {
        if (null != empID) {
            Employee employee = employeeService.getEmpByID(empID);
            employeeService.deleteEmployee(employee);
        }
        String sendParam = "?depID=".concat(String.valueOf(depID));
        return "redirect:/employeesList".concat(sendParam);
    }

    @RequestMapping(value = "/empSave", method = RequestMethod.POST)
    @ResponseBody
    public String empSave(Long depId, Employee employee, Model model) throws DaoExp {
        try {
            employeeService.updateEmployee(employee, depId);
        } catch (ValidateExp exp) {
            Long empId = employee.getId();

            model.addAttribute("depID", depId);
            model.addAttribute("empID", empId);
            model.addAttribute("errorMap", exp.getErrorMap());
            return "empEdit";
        }
        String sendParam = "?depID=".concat(String.valueOf(depId));
        return "redirect:/employeesList".concat(sendParam);
    }

}