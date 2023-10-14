package org.rent.circle.vendor.api.service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface VendorMapper {

    Vendor toModel(SaveVendorDto saveVendor);

    void updateVendor(UpdateVendorDto updateVendorInfo, @MappingTarget Vendor vendor);

    @Mapping(target = "workers", source = "workers")
    VendorDto toDto(Vendor vendor);

    @Mapping(target = "workers", source = "workers")
    List<VendorDto> toDtoList(List<Vendor> vendors);
}
