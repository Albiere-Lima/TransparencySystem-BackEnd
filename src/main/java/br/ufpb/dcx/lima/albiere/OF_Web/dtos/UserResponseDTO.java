package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getNotifyDailySummary() {
        return notifyDailySummary;
    }

    public void setNotifyDailySummary(Boolean notifyDailySummary) {
        this.notifyDailySummary = notifyDailySummary;
    }

    public Boolean getNotifyMonthlyReports() {
        return notifyMonthlyReports;
    }

    public void setNotifyMonthlyReports(Boolean notifyMonthlyReports) {
        this.notifyMonthlyReports = notifyMonthlyReports;
    }

    public Boolean getNotifyNewExpenses() {
        return notifyNewExpenses;
    }

    public void setNotifyNewExpenses(Boolean notifyNewExpenses) {
        this.notifyNewExpenses = notifyNewExpenses;
    }

    public Boolean getNotifySystemAlerts() {
        return notifySystemAlerts;
    }

    public void setNotifySystemAlerts(Boolean notifySystemAlerts) {
        this.notifySystemAlerts = notifySystemAlerts;
    }

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

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getCampus() { return campus; }
    public String getDepartment() { return department; }
    public String getBio() { return bio; }
    public String getRole() { return role; }
    public String getInitials() { return initials; }
    public String getAvatarColor() { return avatarColor; }
    public String getPicture() { return picture; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public void setCampus(String campus) { this.campus = campus; }
    public void setDepartment(String department) { this.department = department; }
    public void setBio(String bio) { this.bio = bio; }
    public void setRole(String role) { this.role = role; }
    public void setInitials(String initials) { this.initials = initials; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }
    public void setPicture(String picture) { this.picture = picture; }
}