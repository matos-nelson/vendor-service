package org.rent.circle.vendor.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
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
    public void saveVendor_WhenGivenInvalidManagerId_ShouldThrowException() {
        // Arrange
        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .build();

        // Act
        // Assert
        assertThrows(ConstraintViolationException.class, () -> vendorService.saveVendor(saveVendorDto, null));
    }

    @Test
    public void saveVendor_WhenCalled_ShouldReturnSavedVendorId() {
        // Arrange
        String managerId = "1";
        SaveVendorDto saveVendorDto = SaveVendorDto.builder()
            .build();

        Vendor vendor = new Vendor();
        vendor.setId(100L);
        when(vendorMapper.toModel(saveVendorDto)).thenReturn(vendor);

        // Act
        Long result = vendorService.saveVendor(saveVendorDto, managerId);

        // Assert
        assertNotNull(result);
        assertEquals(vendor.getId(), result);
        assertEquals(managerId, vendor.getManagerId());
    }

    @Test
    public void updateVendorInfo_WhenGivenAnInvalidManagerId_ShouldThrowException() {
        // Arrange
        long vendorId = 1L;
        UpdateVendorDto updateResidentDto = UpdateVendorDto.builder().build();

        // Act
        // Assert
        assertThrows(ConstraintViolationException.class, () ->
            vendorService.updateVendorInfo(vendorId, null, updateResidentDto));
    }

    @Test
    public void updateVendorInfo_WhenVendorIsNotFound_ShouldReturnNotUpdate() {
        // Arrange
        long vendorId = 1L;
        String managerId = "2";
        UpdateVendorDto updateResidentDto = UpdateVendorDto.builder().build();
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(null);

        // Act
        vendorService.updateVendorInfo(vendorId, managerId, updateResidentDto);

        // Assert
        verify(vendorMapper, never()).updateVendor(updateResidentDto, null);
        verify(vendorRepository, never()).persist(Mockito.any(Vendor.class));
    }

    @Test
    public void updateVendorInfo_WhenCalled_ShouldUpdate() {
        // Arrange
        Long vendorId = 1L;
        String managerId = "2";

        Vendor vendor = new Vendor();
        vendor.setId(vendorId);

        UpdateVendorDto updateVendorInfo = UpdateVendorDto.builder()
            .name("Updated Name")
            .phone("9876543210")
            .email("updated@email.com")
            .build();
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(vendor);

        // Act
        vendorService.updateVendorInfo(vendorId, managerId, updateVendorInfo);

        // Assert
        verify(vendorMapper, times(1)).updateVendor(updateVendorInfo, vendor);
        verify(vendorRepository, times(1)).persist(vendor);
    }

    @Test
    public void getVendor_WhenVendorIsNotFound_ShouldReturnNull() {
        // Arrange
        Long vendorId = 1L;
        String managerId = "2";
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(null);

        // Act
        VendorDto result = vendorService.getVendor(vendorId, managerId);

        // Assert
        assertNull(result);
    }

    @Test
    public void getVendor_WhenVendorIsFound_ShouldReturnVendor() {
        // Arrange
        Long vendorId = 1L;
        String managerId = "2";
        Vendor vendor = new Vendor();
        vendor.setId(vendorId);
        vendor.setManagerId(managerId);

        VendorDto vendorDto = VendorDto.builder()
            .build();
        when(vendorRepository.findVendor(vendorId, managerId)).thenReturn(vendor);
        when(vendorMapper.toDto(vendor)).thenReturn(vendorDto);

        // Act
        VendorDto result = vendorService.getVendor(vendorId, managerId);

        // Assert
        assertNotNull(result);
        assertEquals(vendorDto.getId(), result.getId());
    }

    @Test
    public void getVendors_WhenVendorsWithGivenManagerIdAreNotFound_ShouldReturnEmptyList() {
        // Arrange
        String managerId = "1";
        int page = 2;
        int pageSize = 10;

        when(vendorRepository.findVendors(managerId, true, page, pageSize)).thenReturn(null);

        // Act
        List<VendorDto> result = vendorService.getVendors(managerId, true, page, pageSize);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void getVendors_WhenVendorsWithGivenManagerIdIdAreFound_ShouldReturnList() {
        // Arrange
        String managerId = "1";
        int page = 2;
        int pageSize = 10;
        List<Vendor> vendors = Collections.singletonList(new Vendor());
        when(vendorRepository.findVendors(managerId, false, page, pageSize)).thenReturn(vendors);
        when(vendorMapper.toDtoList(vendors)).thenReturn(
            Collections.singletonList(new VendorDto()));

        // Act
        List<VendorDto> result = vendorService.getVendors(managerId, false, page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
