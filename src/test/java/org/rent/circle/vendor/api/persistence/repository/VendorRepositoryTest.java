package org.rent.circle.vendor.api.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class VendorRepositoryTest {

    @Inject
    VendorRepository vendorRepository;

    @Test
    @TestTransaction
    public void findVendor_WhenVendorDoesNotExist_ShouldReturnNull() {
        // Arrange

        // Act
        Vendor result = vendorRepository.findVendor(123L, 456L);

        // Assert
        assertNull(result);
    }

    @Test
    @TestTransaction
    public void findVendor_WhenVendorDoesExist_ShouldReturnVendor() {
        // Arrange

        // Act
        Vendor result = vendorRepository.findVendor(400L, 500L);

        // Assert
        assertNotNull(result);
    }

    @Test
    @TestTransaction
    public void findVendors_WhenVendorsDoExist_ShouldReturnVendors() {
        // Arrange

        // Act
        List<Vendor> result = vendorRepository.findVendors(500L, false, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getWorkers().size());
    }

    @Test
    @TestTransaction
    public void findVendors_WhenVendorsDoNotExistInPage_ShouldReturnNoVendors() {
        // Arrange

        // Act
        List<Vendor> result = vendorRepository.findVendors(500L, false, 10, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @TestTransaction
    public void findVendors_WhenVendorsDoExistAndActiveWorkersAreFiltered_ShouldReturnVendorsActiveWorkersOnly() {
        // Arrange

        // Act
        List<Vendor> result = vendorRepository.findVendors(500L, true, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getWorkers().size());
    }

    @Test
    @TestTransaction
    public void findVendors_WhenVendorsDoExistAndActiveWorkersAreNotFiltered_ShouldReturnVendorsActiveWorkersOnly() {
        // Arrange

        // Act
        List<Vendor> result = vendorRepository.findVendors(500L, false, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getWorkers().size());
    }
}
