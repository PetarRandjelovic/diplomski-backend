package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.ImageDataRecord;
import org.example.diplomski.data.dto.UserProfileRecord;
import org.example.diplomski.data.entites.ImageData;
import org.example.diplomski.data.entites.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile map(UserProfileRecord userProfileRecord);


    UserProfileRecord map(UserProfile userProfile);



    UserProfile toEntity(UserProfileRecord dto);
    UserProfileRecord toDto(UserProfile entity);

    ImageData toEntity(ImageDataRecord dto);
    ImageDataRecord toDto(ImageData entity);


}
