package br.ufpb.dcx.lima.albiere.OF_Web.dtos;

public record UserNotificationsDTO(
        Boolean notifyDailySummary,
        Boolean notifyMonthlyReports,
        Boolean notifyNewExpenses,
        Boolean notifySystemAlerts
) {}