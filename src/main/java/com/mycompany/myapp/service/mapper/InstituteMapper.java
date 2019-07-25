package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.InstituteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Institute} and its DTO {@link InstituteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstituteMapper extends EntityMapper<InstituteDTO, Institute> {


}
