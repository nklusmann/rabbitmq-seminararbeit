package com.example.rabbitmq_publisher.controller;

import com.example.rabbitmq_publisher.records.StatusReportRequestDto;
import com.example.rabbitmq_publisher.services.RabbitMQProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rabbitmq_publisher.constants.PathConstants.PATH_REST;
import static com.example.rabbitmq_publisher.constants.PathConstants.PATH_STATUS_REPORT;

@RestController
@RequestMapping(PATH_REST)
public class StatusReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitMQProducer producer;

    public StatusReportController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @Tag(name = "Status report")
    @Operation(
            summary = "Report a status",
            description = "At this endpoint a status message can be reported. It will be buffered in a local " +
                    "rabbitmq and pushed to the upstream rabbitmq as soon as a stable connection is available.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Contains status report message",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatusReportRequestDto.class),
                            examples = @ExampleObject(
                                    value = "{ \"statusReportMessage\": \"New status report message\" }")
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reported status successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Status report message in request body cannot be empty or null"
                    )
            }
    )
    @PostMapping(path = PATH_STATUS_REPORT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reportStatusMessage(
            @Valid @RequestBody StatusReportRequestDto statusReportRequestDto
    ) {
        if (logger.isInfoEnabled()) {
            logger.info("Report status with statusReportMessage: {}", statusReportRequestDto.statusReportMessage());
        }

        producer.publishMessage(statusReportRequestDto.statusReportMessage());

        return ResponseEntity.ok("Reported status successfully");
    }

}
