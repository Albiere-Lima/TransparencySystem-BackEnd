package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String registrationNumber;
    private String campus;
    private String department;
    private String bio;
    private String role;
    private String initials;
    private String avatarColor;
    private String picture;
    private String password;

    private Boolean notifyDailySummary;
    private Boolean notifyMonthlyReports;
    private Boolean notifyNewExpenses;
    private Boolean notifySystemAlerts;

    public UserResponseDTO(String password, Boolean notifyDailySummary, Boolean notifyMonthlyReports, Boolean notifyNewExpenses, Boolean notifySystemAlerts) {
        this.password = password;
        this.notifyDailySummary = notifyDailySummary;
        this.notifyMonthlyReports = notifyMonthlyReports;
        this.notifyNewExpenses = notifyNewExpenses;
        this.notifySystemAlerts = notifySystemAlerts;
    }

    public UserResponseDTO(Long id, String name, String surname, String email, String phone,
                           String registrationNumber, String campus, String department,
                           String bio, String role, String initials, String avatarColor, String picture, String password, Boolean notifyDailySummary, Boolean notifyMonthlyReports, Boolean notifyNewExpenses, Boolean notifySystemAlerts) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.registrationNumber = registrationNumber;
        this.campus = campus;
        this.department = department;
        this.bio = bio;
        this.role = role;
        this.initials = initials;
        this.avatarColor = avatarColor;
        this.picture = picture;
        this.password = password;
        this.notifyDailySummary = notifyDailySummary;
        this.notifyMonthlyReports = notifyMonthlyReports;
        this.notifyNewExpenses = notifyNewExpenses;
        this.notifySystemAlerts = notifySystemAlerts;
    }

}