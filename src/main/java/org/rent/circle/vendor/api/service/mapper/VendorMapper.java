package org.rent.circle.vendor.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.rent.circle.vendor.api.dto.CreateVendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface VendorMapper {

    Vendor toModel(CreateVendorDto createVendor);
}
