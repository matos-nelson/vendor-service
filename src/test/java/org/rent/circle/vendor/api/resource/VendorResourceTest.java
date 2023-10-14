package org.rent.circle.vendor.api.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Collections;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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
        UpdateVendorDto updateVendorDto = UpdateVendorDto.builder()
            .build();

        // Act
        // Assert
        given()
            .contentType("application/json")
            .body(updateVendorDto)
            .when()
            .patch("/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void PATCH_WhenGivenAValidRequestToUpdateVendor_ShouldReturnNoContent() {
        // Arrange
        long vendorId = 100L;
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
            .patch("/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void GET_WhenAVendorCantBeFound_ShouldReturnNoContent() {
        // Arrange

        // Act
        // Assert
        given()
            .when()
            .get("/1/owner/2")
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void GET_WhenVendorIsFound_ShouldReturnVendor() {
        // Arrange

        // Act
        // Assert
        given()
            .when()
            .get("/400/owner/500")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("ownerId", is(500),
                "addressId", is(600),
                "name", is("First Vendor"),
                "email", is("vendor@email.com"),
                "phone", is("1234567890"),
                "workers", is(Matchers.hasSize(1)),
                "workers[0].name", is("First Worker"),
                "workers[0].email", is("worker@email.com"),
                "workers[0].phone", is("3216540987"),
                "workers[0].active", is(true)
            );
    }
}
