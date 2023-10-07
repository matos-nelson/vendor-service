package org.rent.circle.vendor.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.CreateVendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.repository.VendorRepository;
import org.rent.circle.vendor.api.service.mapper.VendorMapper;

@QuarkusTest
public class VendorServiceTest {

    @InjectMock
    VendorRepository vendorRepository;

    @InjectMock
    VendorMapper vendorMapper;

    @Inject
    VendorService vendorService;

    @Test
    public void saveVendor_WhenCalled_ShouldReturnSavedVendorId() {
        // Arrange
        CreateVendorDto createVendorDto = CreateVendorDto.builder()
            .ownerId(1L)
            .build();

        Vendor vendor = new Vendor();
        vendor.setId(100L);
        when(vendorMapper.toModel(createVendorDto)).thenReturn(vendor);

        // Act
        Long result = vendorService.saveVendor(createVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(vendor.getId(), result);
    }
}
