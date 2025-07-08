package com.zuzu.sg.review.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private Boolean isShowReviewResponse;
    private Double rating;
    private String checkInDateMonthYear;
    @Column(columnDefinition = "TEXT")
    private String encryptedReviewData;
    private String formattedRating;
    private String formattedReviewDate;
    private String ratingText;
    private String responderName;
    private String responseDateText;
    private String responseTranslateSource;
    @Column(columnDefinition = "TEXT")
    private String reviewComments;
    @Column(columnDefinition = "TEXT")
    private String reviewNegatives;
    @Column(columnDefinition = "TEXT")
    private String reviewPositives;
    private String reviewProviderLogo;
    private String reviewProviderText;
    private String reviewTitle;
    private String translateSource;
    private String translateTarget;
    private LocalDateTime reviewDate;
    private String originalTitle;
    @Column(columnDefinition = "TEXT")
    private String originalComment;
    private String formattedResponseDate;

    @ManyToOne
    @JoinColumn(name = "reviewer_country_id")
    private Country reviewerCountry;

    private String reviewerDisplayName;
    private Integer reviewerLengthOfStay;

    @ManyToOne
    @JoinColumn(name = "reviewer_group_id")
    private Group reviewerGroup;

    @ManyToOne
    @JoinColumn(name = "reviewer_room_type_id")
    private RoomType reviewerRoomType;

    private Integer reviewerReviewedCount;
    private Boolean reviewerIsExpert;
    private Boolean reviewerShowGlobalIcon;
    private Boolean reviewerShowReviewedCount;

    public Review() {
    }
    public Review(Long reviewId, Hotel hotel, Provider provider, Boolean isShowReviewResponse, Double rating, String checkInDateMonthYear, String encryptedReviewData, String formattedRating, String formattedReviewDate, String ratingText, String responderName, String responseDateText, String responseTranslateSource, String reviewComments, String reviewNegatives, String reviewPositives, String reviewProviderLogo, String reviewProviderText, String reviewTitle, String translateSource, String translateTarget, LocalDateTime reviewDate, String originalTitle, String originalComment, String formattedResponseDate, Country reviewerCountry, String reviewerDisplayName, Integer reviewerLengthOfStay, Group reviewerGroup, RoomType reviewerRoomType, Integer reviewerReviewedCount, Boolean reviewerIsExpert, Boolean reviewerShowGlobalIcon, Boolean reviewerShowReviewedCount) {
        this.reviewId = reviewId;
        this.hotel = hotel;
        this.provider = provider;
        this.isShowReviewResponse = isShowReviewResponse;
        this.rating = rating;
        this.checkInDateMonthYear = checkInDateMonthYear;
        this.encryptedReviewData = encryptedReviewData;
        this.formattedRating = formattedRating;
        this.formattedReviewDate = formattedReviewDate;
        this.ratingText = ratingText;
        this.responderName = responderName;
        this.responseDateText = responseDateText;
        this.responseTranslateSource = responseTranslateSource;
        this.reviewComments = reviewComments;
        this.reviewNegatives = reviewNegatives;
        this.reviewPositives = reviewPositives;
        this.reviewProviderLogo = reviewProviderLogo;
        this.reviewProviderText = reviewProviderText;
        this.reviewTitle = reviewTitle;
        this.translateSource = translateSource;
        this.translateTarget = translateTarget;
        this.reviewDate = reviewDate;
        this.originalTitle = originalTitle;
        this.originalComment = originalComment;
        this.formattedResponseDate = formattedResponseDate;
        this.reviewerCountry = reviewerCountry;
        this.reviewerDisplayName = reviewerDisplayName;
        this.reviewerLengthOfStay = reviewerLengthOfStay;
        this.reviewerGroup = reviewerGroup;
        this.reviewerRoomType = reviewerRoomType;
        this.reviewerReviewedCount = reviewerReviewedCount;
        this.reviewerIsExpert = reviewerIsExpert;
        this.reviewerShowGlobalIcon = reviewerShowGlobalIcon;
        this.reviewerShowReviewedCount = reviewerShowReviewedCount;
    }
}
