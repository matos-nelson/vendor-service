package org.rent.circle.vendor.api.dto;

import io.quarkus.test.junit.QuarkusTest;
import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CreateVendorDtoTest {

    @Test
    public void CreateVendorDto_SettersAndGetters_ShouldWork() {
        // Arrange
        BeanTester beanTester = new BeanTester();

        // Act
        beanTester.testBean(CreateVendorDto.class);

        // Assert
    }
}
