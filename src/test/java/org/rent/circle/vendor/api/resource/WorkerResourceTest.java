package org.rent.circle.vendor.api.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.annotation.AuthUser;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;

@QuarkusTest
@TestHTTPEndpoint(WorkerResource.class)
@QuarkusTestResource(H2DatabaseTestResource.class)
@AuthUser
public class WorkerResourceTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @TestSecurity(user = "update_user")
    public @interface UpdateWorkerUser {

    }

    @Test
    public void Post_WhenGivenAnInValidRequestToSave_ShouldReturnBadRequest() {
        // Arrange
        long vendorId = 100L;
        SaveWorkerDto saveWorkerDto = SaveWorkerDto.builder().build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(saveWorkerDto)
            .when()
            .post("/vendor/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @UpdateWorkerUser
    public void Post_WhenGivenAValidRequestToSave_ShouldReturnSavedWorkerId() {
        // Arrange
        long vendorId = 100L;
        SaveWorkerDto saveWorkerDto = SaveWorkerDto.builder()
            .email("create@worker.com")
            .name("Create Worker")
            .phone("4561237890")
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(saveWorkerDto)
            .when()
            .post("/vendor/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is("2"));
    }

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
            .active(false)
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
