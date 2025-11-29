package com.example.Ej2.dto;

import com.example.Ej2.domain.Arcade;

import java.math.BigDecimal;

public record ArcadeEstimatedIncome(Arcade arcade, BigDecimal estimatedIncome) {
}
