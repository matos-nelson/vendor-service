package org.rent.circle.vendor.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
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
        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .ownerId(1L)
            .build();

        Vendor vendor = new Vendor();
        vendor.setId(100L);
        when(vendorMapper.toModel(saveVendorDto)).thenReturn(vendor);

        // Act
        Long result = vendorService.saveVendor(saveVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(vendor.getId(), result);
    }

    @Test
    public void updateVendorInfo_WhenVendorIsNotFound_ShouldReturnNotUpdate() {
        // Arrange
        long vendorId = 1L;
        long ownerId = 2L;
        UpdateVendorDto updateResidentDto = UpdateVendorDto.builder().build();
        when(vendorRepository.findVendor(vendorId, ownerId)).thenReturn(null);

        // Act
        vendorService.updateVendorInfo(vendorId, ownerId, updateResidentDto);

        // Assert
        verify(vendorMapper, never()).updateVendor(updateResidentDto, null);
        verify(vendorRepository, never()).persist(Mockito.any(Vendor.class));
    }

    @Test
    public void updateVendorInfo_WhenCalled_ShouldUpdate() {
        // Arrange
        Long vendorId = 1L;
        Long ownerId = 2L;

        Vendor vendor = new Vendor();
        vendor.setId(vendorId);

        UpdateVendorDto updateVendorInfo = UpdateVendorDto.builder()
            .name("Updated Name")
            .phone("9876543210")
            .email("updated@email.com")
            .build();
        when(vendorRepository.findVendor(vendorId, ownerId)).thenReturn(vendor);

        // Act
        vendorService.updateVendorInfo(vendorId, ownerId, updateVendorInfo);

        // Assert
        verify(vendorMapper, times(1)).updateVendor(updateVendorInfo, vendor);
        verify(vendorRepository, times(1)).persist(vendor);
    }
}
