package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ApplicationlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Applicationlog} and its DTO {@link ApplicationlogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationlogMapper extends EntityMapper<ApplicationlogDTO, Applicationlog> {


}
