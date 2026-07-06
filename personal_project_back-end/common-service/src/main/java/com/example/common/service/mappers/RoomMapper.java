package com.example.common.service.mappers;

import com.example.common.service.entities.RoomEntity;
import com.example.common.service.models.RoomModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomEntity toEntity(RoomModel model);
    RoomModel toModel(RoomEntity entity);

    List<RoomModel> toModelList(List<RoomEntity> entities);
    List<RoomEntity> toEntityList(List<RoomModel> models);
}
