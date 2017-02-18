package com.bbh.web.rest;

import com.bbh.domain.EmployeeHolidayReport;
import com.bbh.service.EmployeeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tomasz Kasprzycki
 */
@RestController
@RequestMapping("/training")
public class EmployeeReportResource {

    private final EmployeeReportService employeeReportService;

    @Autowired
    public EmployeeReportResource(EmployeeReportService employeeReportService) {
        this.employeeReportService = employeeReportService;
    }


    @GetMapping("/employeesreport")
    public List<EmployeeHolidayReport> updateEmployee()  {
        return employeeReportService.getEmployeeHolidayReport();
    }

}
