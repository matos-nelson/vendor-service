package org.rent.circle.vendor.api.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.common.mapper.TypeRef;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.annotation.AuthUser;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;

@QuarkusTest
@TestHTTPEndpoint(VendorResource.class)
@QuarkusTestResource(H2DatabaseTestResource.class)
@AuthUser
public class VendorResourceTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @TestSecurity(user = "new_user")
    public @interface NoVendorUser {

    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @TestSecurity(user = "update_user")
    public @interface UpdateVendorUser {

    }

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
    @UpdateVendorUser
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

        given()
            .when()
            .get("/" + vendorId)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("addressId", is(300),
                "name", is(updateVendorDto.getName()),
                "email", is(updateVendorDto.getEmail()),
                "phone", is(updateVendorDto.getPhone()),
                "workers", is(Matchers.hasSize(0))
            );
    }

    @Test
    @NoVendorUser
    public void GET_WhenAVendorCantBeFound_ShouldReturnNoContent() {
        // Arrange

        // Act
        // Assert
        given()
            .when()
            .get("/1")
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
            .get("/400")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("addressId", is(600),
                "name", is("First Vendor"),
                "email", is("vendor@email.com"),
                "phone", is("1234567890"),
                "workers", is(Matchers.hasSize(2)),
                "workers[0].name", is("First Worker"),
                "workers[0].email", is("worker@email.com"),
                "workers[0].phone", is("3216540987"),
                "workers[0].active", is(true),
                "workers[1].name", is("UnActive Worker"),
                "workers[1].email", is("unactiveworker@email.com"),
                "workers[1].phone", is("5896734599"),
                "workers[1].active", is(false)
            );
    }

    @Test
    @NoVendorUser
    public void GET_getVendors_WhenVendorsCantBeFound_ShouldReturnNoData() {
        // Arrange

        // Act
        // Assert
        given()
            .when()
            .get("?page=0&pageSize=10")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is("[]"));
    }

    @Test
    public void GET_getVendors_WhenVendorsAreFound_ShouldReturnData() {
        // Arrange

        // Act
        List<VendorDto> result = given()
            .when()
            .get("?page=0&pageSize=10&filterActiveWorkers=true")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .as(new TypeRef<>() {
            });

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(400L, result.get(0).getId());
        assertEquals(600L, result.get(0).getAddressId());
        assertEquals("First Vendor", result.get(0).getName());
        assertEquals("1234567890", result.get(0).getPhone());
        assertEquals("vendor@email.com", result.get(0).getEmail());
        assertEquals(1, result.get(0).getWorkers().size());
        assertEquals(700L, result.get(0).getWorkers().get(0).getId());
        assertEquals("First Worker", result.get(0).getWorkers().get(0).getName());
        assertEquals("3216540987", result.get(0).getWorkers().get(0).getPhone());
        assertEquals("worker@email.com", result.get(0).getWorkers().get(0).getEmail());
        assertTrue(result.get(0).getWorkers().get(0).isActive());
    }

    @Test
    public void GET_getVendors_WhenFailsValidation_ShouldReturnBadRequest() {
        // Arrange

        // Act
        // Assert
        given()
            .when()
            .get("?page=0")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
