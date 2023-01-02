package si.fri.rso.ratingsmicroservice.models.converters;

import si.fri.rso.ratingsmicroservice.lib.Rating;
import si.fri.rso.ratingsmicroservice.models.entities.RatingEntity;

public class RatingConverter {

    public static Rating toDto(RatingEntity entity) {

        Rating dto = new Rating();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setRating(entity.getRating());

        return dto;

    }

    public static RatingEntity toEntity(Rating dto) {

        RatingEntity entity = new RatingEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setRating(dto.getRating());

        return entity;

    }

}
