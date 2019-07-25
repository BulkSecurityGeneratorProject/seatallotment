package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SeatPartitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SeatPartition} and its DTO {@link SeatPartitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeatPartitionMapper extends EntityMapper<SeatPartitionDTO, SeatPartition> {


}
