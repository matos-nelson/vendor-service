package org.rent.circle.vendor.api.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Collections;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;

@QuarkusTest
@TestHTTPEndpoint(VendorResource.class)
@QuarkusTestResource(H2DatabaseTestResource.class)
public class VendorResourceTest {

    @Test
    public void Post_WhenGivenAValidRequestToSave_ShouldReturnSavedApplicationId() {
        // Arrange
        SaveWorkerDto saveWorkerDto = SaveWorkerDto.builder()
            .email("create@worker.com")
            .name("Create Worker")
            .phone("4561237890")
            .build();

        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .addressId(1L)
            .email("create@vender.com")
            .name("Create Vendor")
            .phone("1234567890")
            .ownerId(2L)
            .workers(Collections.singletonList(saveWorkerDto))
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(saveVendorDto)
            .when()
            .post()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is("1"));
    }

    @Test
    public void Post_WhenGivenAnInValidRequestToSave_ShouldReturnBadRequest() {
        // Arrange
        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .ownerId(1L)
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(saveVendorDto)
            .when()
            .post()
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void PATCH_WhenGivenRequestToUpdateVendorFailsValidation_ShouldReturnBadRequest() {
        // Arrange
        long vendorId = 300L;
        long ownerId = 400L;
        UpdateVendorDto updateVendorDto = UpdateVendorDto.builder()
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(updateVendorDto)
            .when()
            .patch("/" + vendorId + "/owner/" + ownerId)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void PATCH_WhenGivenAValidRequestToUpdateVendor_ShouldReturnNoContent() {
        // Arrange
        long vendorId = 100L;
        long ownerId = 200L;
        UpdateVendorDto updateVendorDto = UpdateVendorDto.builder()
            .phone("9999999999")
            .name("New Name")
            .email("new@email.com")
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(updateVendorDto)
            .when()
            .patch("/" + vendorId + "/owner/" + ownerId)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
