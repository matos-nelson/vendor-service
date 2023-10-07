package org.rent.circle.vendor.api.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
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
}
