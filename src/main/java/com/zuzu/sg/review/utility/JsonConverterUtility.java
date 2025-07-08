package com.zuzu.sg.review.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zuzu.sg.review.dto.HotelReviewDTO;
import com.zuzu.sg.review.entities.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class JsonConverterUtility {

    private final ObjectMapper objectMapper;

    public JsonConverterUtility() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public ConvertedEntities convertJsonToEntities(String jsonString) throws IOException {
        HotelReviewDTO dto = objectMapper.readValue(jsonString, HotelReviewDTO.class);

        Hotel hotel = new Hotel(dto.getHotelId(), dto.getHotelName(), dto.getPlatform());

        Provider provider = new Provider(dto.getOverallByProviders().get(0).getProviderId(), dto.getOverallByProviders().get(0).getProvider());

        Country country = new Country(
                dto.getComment().getReviewerInfo().getCountryId(),
                dto.getComment().getReviewerInfo().getCountryName(),
                dto.getComment().getReviewerInfo().getFlagName()
        );

        Group group = new Group(
                dto.getComment().getReviewerInfo().getReviewGroupId(),
                dto.getComment().getReviewerInfo().getReviewGroupName()
        );

        RoomType roomType = new RoomType(
                dto.getComment().getReviewerInfo().getRoomTypeId(),
                dto.getComment().getReviewerInfo().getRoomTypeName()
        );

        ProviderGradeRating providerGradeRating = null;
        Grade grade = null;
        for (Map.Entry<String, Double> entry : dto.getOverallByProviders().get(0).getGrades().entrySet()) {
            String gradeName = entry.getKey();
            Double actualRating = entry.getValue();
            grade = new Grade((long) gradeName.hashCode(),
                    gradeName
            );

            ProviderGradeRatingId pgrId = new ProviderGradeRatingId(provider.getProviderId(), grade.getGradeId());
            providerGradeRating = new ProviderGradeRating(pgrId, provider, grade, actualRating);
        }


        ProviderSummary providerSummary = new ProviderSummary(
                null,
                provider,
                dto.getOverallByProviders().get(0).getOverallScore(),
                dto.getOverallByProviders().get(0).getReviewCount()
        );


        Review review = new Review();
        review.setReviewId(dto.getComment().getHotelReviewId());
        review.setHotel(hotel);
        review.setProvider(provider);
        review.setIsShowReviewResponse(dto.getComment().getIsShowReviewResponse());
        review.setRating(dto.getComment().getRating());
        review.setCheckInDateMonthYear(dto.getComment().getCheckInDateMonthAndYear());
        review.setEncryptedReviewData(dto.getComment().getEncryptedReviewData());
        review.setFormattedRating(dto.getComment().getFormattedRating());
        review.setFormattedReviewDate(dto.getComment().getFormattedReviewDate());
        review.setRatingText(dto.getComment().getRatingText());
        review.setResponderName(dto.getComment().getResponderName());
        review.setResponseDateText(dto.getComment().getResponseDateText());
        review.setResponseTranslateSource(dto.getComment().getResponseTranslateSource());
        review.setReviewComments(dto.getComment().getReviewComments());
        review.setReviewNegatives(dto.getComment().getReviewNegatives());
        review.setReviewPositives(dto.getComment().getReviewPositives());
        review.setReviewProviderLogo(dto.getComment().getReviewProviderLogo());
        review.setReviewProviderText(dto.getComment().getReviewProviderText());
        review.setReviewTitle(dto.getComment().getReviewTitle());
        review.setTranslateSource(dto.getComment().getTranslateSource());
        review.setTranslateTarget(dto.getComment().getTranslateTarget());
        review.setReviewDate(dto.getComment().getReviewDate());
        review.setOriginalTitle(dto.getComment().getOriginalTitle());
        review.setOriginalComment(dto.getComment().getOriginalComment());
        review.setFormattedResponseDate(dto.getComment().getFormattedResponseDate());
        review.setReviewerCountry(country);
        review.setReviewerDisplayName(dto.getComment().getReviewerInfo().getDisplayMemberName());
        review.setReviewerLengthOfStay(dto.getComment().getReviewerInfo().getLengthOfStay());
        review.setReviewerGroup(group);
        review.setReviewerRoomType(roomType);
        review.setReviewerReviewedCount(dto.getComment().getReviewerInfo().getReviewerReviewedCount());
        review.setReviewerIsExpert(dto.getComment().getReviewerInfo().getIsExpertReviewer());
        review.setReviewerShowGlobalIcon(dto.getComment().getReviewerInfo().getIsShowGlobalIcon());
        review.setReviewerShowReviewedCount(dto.getComment().getReviewerInfo().getIsShowReviewedCount());


        return new ConvertedEntities(hotel, review, provider, grade, providerGradeRating, country, group, roomType, providerSummary);
    }

    public static class ConvertedEntities {
        public final Hotel hotel;
        public final Review review;
        public final Provider provider;
        public final Grade grade;
        public final ProviderGradeRating provideGradeRating;
        public final Country country;
        public final Group group;
        public final RoomType roomType;
        public final ProviderSummary providerSummary;

        public ConvertedEntities(Hotel hotel, Review review, Provider provider, Grade grade, ProviderGradeRating providerGradeRating, Country country, Group group, RoomType roomType, ProviderSummary providerSummary) {
            this.hotel = hotel;
            this.review = review;
            this.provider = provider;
            this.grade = grade;
            this.provideGradeRating = providerGradeRating;
            this.country = country;
            this.group = group;
            this.roomType = roomType;
            this.providerSummary = providerSummary;
        }
    }
}