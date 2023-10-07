package org.rent.circle.vendor.api.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SaveVendorDtoTest {

    @Test
    public void CreateVendorDto_SettersAndGetters_ShouldWork() {
        // Arrange
        BeanTester beanTester = new BeanTester();

        // Act
        beanTester.testBean(SaveVendorDto.class);

        // Assert
    }
}
