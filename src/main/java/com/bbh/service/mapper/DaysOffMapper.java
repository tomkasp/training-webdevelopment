package com.bbh.service.mapper;

import com.bbh.domain.*;
import com.bbh.service.dto.DaysOffDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DaysOff and its DTO DaysOffDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DaysOffMapper {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "employee.id", target = "employeeId")
    DaysOffDTO daysOffToDaysOffDTO(DaysOff daysOff);

    List<DaysOffDTO> daysOffsToDaysOffDTOs(List<DaysOff> daysOffs);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "employeeId", target = "employee")
    DaysOff daysOffDTOToDaysOff(DaysOffDTO daysOffDTO);

    List<DaysOff> daysOffDTOsToDaysOffs(List<DaysOffDTO> daysOffDTOs);

    default DayOffType dayOffTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        DayOffType dayOffType = new DayOffType();
        dayOffType.setId(id);
        return dayOffType;
    }

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
