package com.fitnesscenter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "membership_plans")
public class MembershipPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Plan name is required")
    @Size(min = 2, max = 100, message = "Plan name must be between 2 and 100 characters")
    @Column(name = "plan_name")
    private String planName;
    
    @Column(length = 1000)
    private String description;
    
    @NotNull(message = "Duration in months is required")
    @Min(value = 1, message = "Duration must be at least 1 month")
    @Column(name = "duration_months")
    private Integer durationMonths;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "setup_fee", precision = 8, scale = 2)
    private BigDecimal setupFee;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type")
    private PlanType planType;
    
    @Column(name = "gym_access")
    private Boolean gymAccess;
    
    @Column(name = "pool_access")
    private Boolean poolAccess;
    
    @Column(name = "group_classes_included")
    private Boolean groupClassesIncluded;
    
    @Column(name = "personal_training_sessions")
    private Integer personalTrainingSessions;
    
    @Column(name = "guest_passes")
    private Integer guestPasses;
    
    @Column(name = "nutrition_consultation")
    private Boolean nutritionConsultation;
    
    @Column(name = "locker_included")
    private Boolean lockerIncluded;
    
    @Column(name = "towel_service")
    private Boolean towelService;
    
    @Column(name = "features", length = 1000)
    private String features;
    
    @Enumerated(EnumType.STRING)
    private PlanStatus status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "membershipPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> members;
    
    public enum PlanType {
        BASIC, PREMIUM, VIP, STUDENT, SENIOR, FAMILY
    }
    
    public enum PlanStatus {
        ACTIVE, INACTIVE, DISCONTINUED
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = PlanStatus.ACTIVE;
        }
        if (gymAccess == null) {
            gymAccess = true;
        }
        if (poolAccess == null) {
            poolAccess = false;
        }
        if (groupClassesIncluded == null) {
            groupClassesIncluded = false;
        }
        if (nutritionConsultation == null) {
            nutritionConsultation = false;
        }
        if (lockerIncluded == null) {
            lockerIncluded = false;
        }
        if (towelService == null) {
            towelService = false;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public MembershipPlan() {}
    
    public MembershipPlan(String planName, String description, Integer durationMonths, BigDecimal price, PlanType planType) {
        this.planName = planName;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
        this.planType = planType;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id =
