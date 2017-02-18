package com.bbh.service;

import com.bbh.domain.EmployeeHolidayReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tomasz Kasprzycki
 */
@Service
public class EmployeeReportService {

    private final JdbcTemplate jdbcTemplate;

    private final String sql =
        "SELECT\n" +
            "    emp.YEARLY_ALLOWENCE \"yearly_allowance\",\n" +
            "    emp.CARRY_OVER_DAYS \"carry_over_days\",\n" +
            "\n" +
            "    emp.CARRY_OVER_DAYS + emp.YEARLY_ALLOWENCE - (SELECT COUNT(*)\n" +
            "                                                  FROM days_off\n" +
            "                                                  WHERE Employee_ID = emp.id) \"available_vacations\",\n" +
            "\n" +
            "    (SELECT COUNT(*)\n" +
            "     FROM days_off\n" +
            "     WHERE Employee_ID = emp.id\n" +
            "           AND DAY_OFF_DATE  >  CURRENT_DATE()) \"planned_vacations\"\n" +
            "\n" +
            "\n" +
            "FROM employee emp, department dept\n" +
            "WHERE emp.department_id = dept.id\n" +
            "group by emp.id;";

    @Autowired
    public EmployeeReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<EmployeeHolidayReport> getEmployeeHolidayReport() {


        List<EmployeeHolidayReport> customers = new ArrayList<EmployeeHolidayReport>();

        final List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : maps) {
            EmployeeHolidayReport employeeHolidayReport = new EmployeeHolidayReport();
            employeeHolidayReport.setYearly_allowance(Integer.parseInt(map.get("yearly_allowance").toString()));
            employeeHolidayReport.setAvailable_vacations(Integer.parseInt(map.get("available_vacations").toString()));
            employeeHolidayReport.setCarry_over_days(Integer.parseInt(map.get("carry_over_days").toString()));
            employeeHolidayReport.setPlanned_vacations(Integer.parseInt(map.get("planned_vacations").toString()));
            customers.add(employeeHolidayReport);

        }


        return customers;

    }


}
