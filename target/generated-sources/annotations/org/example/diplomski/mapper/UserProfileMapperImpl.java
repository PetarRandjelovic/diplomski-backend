package org.example.diplomski.mapper;

import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.profile.ImageDataRecord;
import org.example.diplomski.data.dto.profile.UserProfileRecord;
import org.example.diplomski.data.entites.ImageData;
import org.example.diplomski.data.entites.UserProfile;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T23:22:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfile map(UserProfileRecord userProfileRecord) {
        if ( userProfileRecord == null ) {
            return null;
        }

        UserProfile userProfile = new UserProfile();

        userProfile.setId( userProfileRecord.id() );
        userProfile.setCity( userProfileRecord.city() );

        return userProfile;
    }

    @Override
    public UserProfileRecord map(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        Long id = null;
        String city = null;

        id = userProfile.getId();
        city = userProfile.getCity();

        ImageDataRecord imageDataRecord = null;
        String userEmail = null;

        UserProfileRecord userProfileRecord = new UserProfileRecord( id, city, imageDataRecord, userEmail );

        return userProfileRecord;
    }

    @Override
    public UserProfile toEntity(UserProfileRecord dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfile userProfile = new UserProfile();

        userProfile.setId( dto.id() );
        userProfile.setCity( dto.city() );

        return userProfile;
    }

    @Override
    public UserProfileRecord toDto(UserProfile entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String city = null;

        id = entity.getId();
        city = entity.getCity();

        ImageDataRecord imageDataRecord = null;
        String userEmail = null;

        UserProfileRecord userProfileRecord = new UserProfileRecord( id, city, imageDataRecord, userEmail );

        return userProfileRecord;
    }

    @Override
    public ImageData toEntity(ImageDataRecord dto) {
        if ( dto == null ) {
            return null;
        }

        ImageData.ImageDataBuilder imageData1 = ImageData.builder();

        imageData1.id( dto.id() );
        imageData1.name( dto.name() );
        imageData1.type( dto.type() );
        byte[] imageData = dto.imageData();
        if ( imageData != null ) {
            imageData1.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }

        return imageData1.build();
    }

    @Override
    public ImageDataRecord toDto(ImageData entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String type = null;
        byte[] imageData = null;

        id = entity.getId();
        name = entity.getName();
        type = entity.getType();
        byte[] imageData1 = entity.getImageData();
        if ( imageData1 != null ) {
            imageData = Arrays.copyOf( imageData1, imageData1.length );
        }

        ImageDataRecord imageDataRecord = new ImageDataRecord( id, name, type, imageData );

        return imageDataRecord;
    }
}
