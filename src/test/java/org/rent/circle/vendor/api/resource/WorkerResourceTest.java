package org.rent.circle.vendor.api.resource;

import static io.restassured.RestAssured.given;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;

@QuarkusTest
@TestHTTPEndpoint(WorkerResource.class)
@QuarkusTestResource(H2DatabaseTestResource.class)
public class WorkerResourceTest {

    @Test
    public void PATCH_WhenGivenRequestToUpdateWorkerFailsValidation_ShouldReturnBadRequest() {
        // Arrange
        long workerId = 300L;
        long vendorId = 400L;
        UpdateWorkerDto updateWorkerDto = UpdateWorkerDto.builder()
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(updateWorkerDto)
            .when()
            .patch("/" + workerId + "/vendor/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void PATCH_WhenGivenAValidRequestToUpdateWorker_ShouldReturnNoContent() {
        // Arrange
        long workerId = 700L;
        long vendorId = 400L;
        UpdateWorkerDto updateWorkerDto = UpdateWorkerDto.builder()
            .phone("9999999999")
            .name("New Name")
            .email("new@email.com")
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(updateWorkerDto)
            .when()
            .patch("/" + workerId + "/vendor/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
