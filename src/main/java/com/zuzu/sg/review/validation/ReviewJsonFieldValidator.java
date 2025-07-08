package com.zuzu.sg.review.validation;

import com.zuzu.sg.review.utility.JsonConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewJsonFieldValidator {

    private static final Logger log = LoggerFactory.getLogger(ReviewJsonFieldValidator.class);

    List<String> validationErrors = new ArrayList<>();

    public List<String> validateEntities(JsonConverterUtility.ConvertedEntities allEntities)    {
        if(allEntities.country.getCountryName().isEmpty()) {
            validationErrors.add(String.format("Country name is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Country name is missing in the review for hotelId: {}", allEntities.hotel.getHotelId());
        }
        if(allEntities.roomType.getRoomTypeName().isEmpty())    {
            validationErrors.add(String.format("Room type name is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Room type name is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.group.getGroupName().isEmpty())    {
            validationErrors.add(String.format("Group name is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Group name is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.grade.getGradeName().isEmpty())    {
            validationErrors.add(String.format("Grade name is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Grade name is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.provideGradeRating.getActualRating() == null)    {
            validationErrors.add(String.format("Actual rating is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Actual rating is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.provider.getProviderName().isEmpty())    {
            validationErrors.add(String.format("Provider name is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Provider name is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.providerSummary.getOverallScore() == null)    {
            validationErrors.add(String.format("Overall score is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Overall score is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        if(allEntities.review.getReviewComments().isEmpty() || allEntities.review.getRating() == null)    {
            validationErrors.add(String.format("Review Comments and overall rating is missing in the review for hotelId: %s", allEntities.hotel.getHotelId()));
            log.info("Review Comments and overall rating is missing in the review for hotelId:{}", allEntities.hotel.getHotelId());
        }
        return validationErrors;
    }
}