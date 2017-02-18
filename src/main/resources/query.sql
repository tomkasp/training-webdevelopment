SELECT emp.employee_number
                  , emp.first_name
                  , emp.last_name
                  , dept.name Department
                  , emp.Yearly_Allowance Allowance
                  , emp.Carry_Over_Days “Carry Over”
                  , emp.Carry_Over_Days + emp.Yearly_Allowance -
                                          (SELECT COUNT(*)
                                             FROM days_off
                                          WHERE Employee_ID = emp.ID
                                                AND Day_Off_Type_ID = …) Available vacations
                  , emp.Carry_Over_Days + emp.Yearly_Allowance -
                                          (SELECT COUNT(*)
                                             FROM days_off
                                          WHERE Employee_ID = emp.ID
                                                AND Day_Off_Type_ID = …
                                                AND Date < SYSDATE) Available vacations today
                  ,    (SELECT COUNT(*)
                          FROM days_off
                        WHERE Employee_ID = emp.ID
                             AND Day_Off_Type_ID = …
                             AND Date > SYSDATE) Planned vacations
        FROM employees emp, departments dept
     WHERE emp.department_ID = dept.ID
GROUP BY emp.ID
                  , emp.employee_number
                  , emp.first_name
                  , emp.last_name
                  , emp.Yearly_Allowance
                  , emp. Carry_Over_Days


////////////////

SELECT
    emp.YEARLY_ALLOWENCE "yearly allowance",
    emp.CARRY_OVER_DAYS "carry over days",

    emp.CARRY_OVER_DAYS + emp.YEARLY_ALLOWENCE - (SELECT COUNT(*)
                                                  FROM days_off
                                                  WHERE Employee_ID = emp.id) "available vacations",

    (SELECT COUNT(*)
     FROM days_off
     WHERE Employee_ID = emp.id
           AND DAY_OFF_DATE  >  CURRENT_DATE()) "planned vacations"


FROM employee emp, department dept
WHERE emp.department_id = dept.id
group by emp.id;
