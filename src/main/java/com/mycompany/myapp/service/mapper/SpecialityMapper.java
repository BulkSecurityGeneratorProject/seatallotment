package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SpecialityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Speciality} and its DTO {@link SpecialityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialityMapper extends EntityMapper<SpecialityDTO, Speciality> {


}
