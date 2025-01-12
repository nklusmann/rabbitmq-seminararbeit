package com.example.rabbitmq_publisher.records;

import jakarta.validation.constraints.NotBlank;

public record StatusReportRequestDto(@NotBlank String statusReportMessage) {
}