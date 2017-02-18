package com.bbh.service.mapper;

import com.bbh.domain.*;
import com.bbh.service.dto.DayOffTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DayOffType and its DTO DayOffTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DayOffTypeMapper {

    DayOffTypeDTO dayOffTypeToDayOffTypeDTO(DayOffType dayOffType);

    List<DayOffTypeDTO> dayOffTypesToDayOffTypeDTOs(List<DayOffType> dayOffTypes);

    DayOffType dayOffTypeDTOToDayOffType(DayOffTypeDTO dayOffTypeDTO);

    List<DayOffType> dayOffTypeDTOsToDayOffTypes(List<DayOffTypeDTO> dayOffTypeDTOs);
}
