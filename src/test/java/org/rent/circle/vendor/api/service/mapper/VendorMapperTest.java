package org.rent.circle.vendor.api.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.CreateVendorDto;
import org.rent.circle.vendor.api.dto.CreateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@QuarkusTest
public class VendorMapperTest {

    @Inject
    VendorMapper vendorMapper;

    @Test
    public void toModel_WhenGivenNull_ShouldReturnNull() {
        // Arrange

        // Act
        Vendor result = vendorMapper.toModel(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void toModel_WhenGivenACreateVendorDto_ShouldMap() {
        // Arrange
        CreateVendorDto createVendorDto = CreateVendorDto.builder()
            .addressId(1L)
            .email("create@vender.com")
            .name("Create Vendor")
            .phone("1234567890")
            .ownerId(2L)
            .build();

        // Act
        Vendor result = vendorMapper.toModel(createVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(createVendorDto.getAddressId(), result.getAddressId());
        assertEquals(createVendorDto.getOwnerId(), result.getOwnerId());
        assertEquals(createVendorDto.getEmail(), result.getEmail());
        assertEquals(createVendorDto.getName(), result.getName());
        assertEquals(createVendorDto.getPhone(), result.getPhone());
    }

    @Test
    public void toModel_WhenGivenACreateVendorDtoWithWorkers_ShouldMap() {
        // Arrange

        CreateWorkerDto createWorkerDto = CreateWorkerDto.builder()
            .email("create@worker.com")
            .name("Create Worker")
            .phone("4561237890")
            .build();

        CreateVendorDto createVendorDto = CreateVendorDto.builder()
            .addressId(1L)
            .email("create@vender.com")
            .name("Create Vendor")
            .phone("1234567890")
            .ownerId(2L)
            .workers(Collections.singletonList(createWorkerDto))
            .build();

        // Act
        Vendor result = vendorMapper.toModel(createVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(createVendorDto.getAddressId(), result.getAddressId());
        assertEquals(createVendorDto.getOwnerId(), result.getOwnerId());
        assertEquals(createVendorDto.getEmail(), result.getEmail());
        assertEquals(createVendorDto.getName(), result.getName());
        assertEquals(createVendorDto.getPhone(), result.getPhone());
        assertEquals(createVendorDto.getWorkers().size(), result.getWorkers().size());
        assertEquals(createVendorDto.getWorkers().get(0).getEmail(), result.getWorkers().get(0).getEmail());
        assertEquals(createVendorDto.getWorkers().get(0).getName(), result.getWorkers().get(0).getName());
        assertEquals(createVendorDto.getWorkers().get(0).getPhone(), result.getWorkers().get(0).getPhone());
    }
}
