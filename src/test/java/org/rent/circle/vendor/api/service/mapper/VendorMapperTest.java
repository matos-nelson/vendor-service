package org.rent.circle.vendor.api.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.CreateWorkerDto;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
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
    public void toModel_WhenGivenASaveVendorDto_ShouldMap() {
        // Arrange
        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .addressId(1L)
            .email("create@vender.com")
            .name("Create Vendor")
            .phone("1234567890")
            .ownerId(2L)
            .build();

        // Act
        Vendor result = vendorMapper.toModel(saveVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(saveVendorDto.getAddressId(), result.getAddressId());
        assertEquals(saveVendorDto.getOwnerId(), result.getOwnerId());
        assertEquals(saveVendorDto.getEmail(), result.getEmail());
        assertEquals(saveVendorDto.getName(), result.getName());
        assertEquals(saveVendorDto.getPhone(), result.getPhone());
    }

    @Test
    public void toModel_WhenGivenASaveVendorDtoWithWorkers_ShouldMap() {
        // Arrange

        CreateWorkerDto createWorkerDto = CreateWorkerDto.builder()
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
            .workers(Collections.singletonList(createWorkerDto))
            .build();

        // Act
        Vendor result = vendorMapper.toModel(saveVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(saveVendorDto.getAddressId(), result.getAddressId());
        assertEquals(saveVendorDto.getOwnerId(), result.getOwnerId());
        assertEquals(saveVendorDto.getEmail(), result.getEmail());
        assertEquals(saveVendorDto.getName(), result.getName());
        assertEquals(saveVendorDto.getPhone(), result.getPhone());
        assertEquals(saveVendorDto.getWorkers().size(), result.getWorkers().size());
        assertEquals(saveVendorDto.getWorkers().get(0).getEmail(), result.getWorkers().get(0).getEmail());
        assertEquals(saveVendorDto.getWorkers().get(0).getName(), result.getWorkers().get(0).getName());
        assertEquals(saveVendorDto.getWorkers().get(0).getPhone(), result.getWorkers().get(0).getPhone());
    }
}
