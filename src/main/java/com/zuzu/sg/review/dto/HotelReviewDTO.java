package com.zuzu.sg.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO to map the review json in each line
 */
@Getter
@Setter
public class HotelReviewDTO {
    private Long hotelId;
    private String platform;
    private String hotelName;
    private CommentDTO comment;
    private List<OverallByProviderDTO> overallByProviders;

    @Setter
    @Getter
    public static class CommentDTO {
        private Boolean isShowReviewResponse;
        private Long hotelReviewId;
        private Long providerId;
        private Double rating;
        private String checkInDateMonthAndYear;
        private String encryptedReviewData;
        private String formattedRating;
        private String formattedReviewDate;
        private String ratingText;
        private String responderName;
        private String responseDateText;
        private String responseTranslateSource;
        private String reviewComments;
        private String reviewNegatives;
        private String reviewPositives;
        private String reviewProviderLogo;
        private String reviewProviderText;
        private String reviewTitle;
        private String translateSource;
        private String translateTarget;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime reviewDate;
        private ReviewerInfoDTO reviewerInfo;
        private String originalTitle;
        private String originalComment;
        private String formattedResponseDate;

    }

    @Setter
    @Getter
    public static class ReviewerInfoDTO {
        private String countryName;
        private String displayMemberName;
        private String flagName;
        private String reviewGroupName;
        private String roomTypeName;
        private Long countryId;
        private Integer lengthOfStay;
        private Long reviewGroupId;
        private Long roomTypeId;
        private Integer reviewerReviewedCount;
        private Boolean isExpertReviewer;
        private Boolean isShowGlobalIcon;
        private Boolean isShowReviewedCount;

    }

    @Setter
    @Getter
    public static class OverallByProviderDTO {
        private Long providerId;
        private String provider;
        private Double overallScore;
        private Integer reviewCount;
        private Map<String, Double> grades;

    }
}