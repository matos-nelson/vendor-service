package org.rent.circle.vendor.api.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.model.Worker;

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
            .build();

        // Act
        Vendor result = vendorMapper.toModel(saveVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(saveVendorDto.getAddressId(), result.getAddressId());
        assertEquals(saveVendorDto.getEmail(), result.getEmail());
        assertEquals(saveVendorDto.getName(), result.getName());
        assertEquals(saveVendorDto.getPhone(), result.getPhone());
    }

    @Test
    public void toModel_WhenGivenASaveVendorDtoWithWorkers_ShouldMap() {
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
        Vendor result = vendorMapper.toModel(saveVendorDto);

        // Assert
        assertNotNull(result);
        assertEquals(saveVendorDto.getAddressId(), result.getAddressId());
        assertEquals(saveVendorDto.getEmail(), result.getEmail());
        assertEquals(saveVendorDto.getName(), result.getName());
        assertEquals(saveVendorDto.getPhone(), result.getPhone());
        assertEquals(saveVendorDto.getWorkers().size(), result.getWorkers().size());
        assertEquals(saveVendorDto.getWorkers().get(0).getEmail(), result.getWorkers().get(0).getEmail());
        assertEquals(saveVendorDto.getWorkers().get(0).getName(), result.getWorkers().get(0).getName());
        assertEquals(saveVendorDto.getWorkers().get(0).getPhone(), result.getWorkers().get(0).getPhone());
    }

    @Test
    public void updateVendor_WhenGivenNullUpdateVendorDto_ShouldReturnNull() {
        // Arrange
        Vendor vendor = new Vendor();
        vendor.setManagerId("1");
        vendor.setAddressId(2L);
        vendor.setName("Simple Test");
        vendor.setEmail("simpletest@email.com");
        vendor.setPhone("1234567890");

        // Act
        vendorMapper.updateVendor(null, vendor);

        // Assert
        assertNotNull(vendor);
    }

    @Test
    public void updateVendor_WhenGivenAnUpdateVendorDto_ShouldMap() {
        // Arrange
        Vendor vendor = new Vendor();
        vendor.setManagerId("1");
        vendor.setAddressId(2L);
        vendor.setName("Simple Test");
        vendor.setEmail("simpletest@email.com");
        vendor.setPhone("1234567890");

        UpdateVendorDto updateVendor = UpdateVendorDto.builder()
            .name("Updated Name")
            .email("updated@email.com")
            .phone("9876543210")
            .build();

        // Act
        vendorMapper.updateVendor(updateVendor, vendor);

        // Assert
        assertNotNull(vendor);
        assertEquals(updateVendor.getName(), vendor.getName());
        assertEquals(updateVendor.getEmail(), vendor.getEmail());
        assertEquals(updateVendor.getPhone(), vendor.getPhone());
    }

    @Test
    public void toDto_WhenGivenNull_ShouldReturnNull() {
        // Arrange

        // Act
        VendorDto result = vendorMapper.toDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void toDto_WhenGivenVendor_ShouldMap() {
        // Arrange
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setAddressId(2L);
        vendor.setManagerId("3");
        vendor.setPhone("1234567890");
        vendor.setName("Test Vendor");
        vendor.setEmail("test_vendor@email.com");

        // Act
        VendorDto result = vendorMapper.toDto(vendor);

        // Assert
        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getAddressId(), result.getAddressId());
        assertEquals(vendor.getEmail(), result.getEmail());
        assertEquals(vendor.getName(), result.getName());
        assertEquals(vendor.getPhone(), result.getPhone());
    }

    @Test
    public void toDto_WhenGivenVendorWithWorkers_ShouldMap() {
        // Arrange
        Worker worker = new Worker();
        worker.setId(100L);
        worker.setActive(false);
        worker.setPhone("5678901234");
        worker.setEmail("test_worker@email.com");
        worker.setName("Test Worker");

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setAddressId(2L);
        vendor.setManagerId("3");
        vendor.setPhone("1234567890");
        vendor.setName("Test Vendor");
        vendor.setEmail("test_vendor@email.com");

        worker.setVendor(vendor);
        vendor.setWorkers(Collections.singletonList(worker));

        // Act
        VendorDto result = vendorMapper.toDto(vendor);

        // Assert
        assertNotNull(result);
        assertEquals(vendor.getId(), result.getId());
        assertEquals(vendor.getAddressId(), result.getAddressId());
        assertEquals(vendor.getEmail(), result.getEmail());
        assertEquals(vendor.getName(), result.getName());
        assertEquals(vendor.getPhone(), result.getPhone());
        assertEquals(vendor.getWorkers().size(), result.getWorkers().size());
        assertEquals(vendor.getWorkers().get(0).getId(), result.getWorkers().get(0).getId());
        assertEquals(vendor.getWorkers().get(0).getEmail(), result.getWorkers().get(0).getEmail());
        assertEquals(vendor.getWorkers().get(0).getName(), result.getWorkers().get(0).getName());
        assertEquals(vendor.getWorkers().get(0).getPhone(), result.getWorkers().get(0).getPhone());
        assertFalse(result.getWorkers().get(0).isActive());
    }

    @Test
    public void toDtoList_WhenGivenNull_ShouldReturnNull() {
        // Arrange

        // Act
        List<VendorDto> result = vendorMapper.toDtoList(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void toDtoList_WhenGivenAVendorList_ShouldMap() {
        // Arrange
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setAddressId(2L);
        vendor.setManagerId("3");
        vendor.setPhone("1234567890");
        vendor.setName("Test Vendor");
        vendor.setEmail("test_vendor@email.com");

        // Act
        List<VendorDto> result = vendorMapper.toDtoList(Collections.singletonList(vendor));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vendor.getId(), result.get(0).getId());
        assertEquals(vendor.getAddressId(), result.get(0).getAddressId());
        assertEquals(vendor.getEmail(), result.get(0).getEmail());
        assertEquals(vendor.getName(), result.get(0).getName());
        assertEquals(vendor.getPhone(), result.get(0).getPhone());
    }

    @Test
    public void toDtoList_WhenGivenVendorListWithWorkers_ShouldMap() {
        // Arrange
        Worker worker = new Worker();
        worker.setId(100L);
        worker.setActive(false);
        worker.setPhone("5678901234");
        worker.setEmail("test_worker@email.com");
        worker.setName("Test Worker");

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setAddressId(2L);
        vendor.setManagerId("3");
        vendor.setPhone("1234567890");
        vendor.setName("Test Vendor");
        vendor.setEmail("test_vendor@email.com");

        worker.setVendor(vendor);
        vendor.setWorkers(Collections.singletonList(worker));

        // Act
        List<VendorDto> result = vendorMapper.toDtoList(Collections.singletonList(vendor));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vendor.getId(), result.get(0).getId());
        assertEquals(vendor.getAddressId(), result.get(0).getAddressId());
        assertEquals(vendor.getEmail(), result.get(0).getEmail());
        assertEquals(vendor.getName(), result.get(0).getName());
        assertEquals(vendor.getPhone(), result.get(0).getPhone());
        assertEquals(vendor.getWorkers().size(), result.get(0).getWorkers().size());
        assertEquals(vendor.getWorkers().get(0).getId(), result.get(0).getWorkers().get(0).getId());
        assertEquals(vendor.getWorkers().get(0).getEmail(), result.get(0).getWorkers().get(0).getEmail());
        assertEquals(vendor.getWorkers().get(0).getName(), result.get(0).getWorkers().get(0).getName());
        assertEquals(vendor.getWorkers().get(0).getPhone(), result.get(0).getWorkers().get(0).getPhone());
        assertFalse(result.get(0).getWorkers().get(0).isActive());
    }
}
