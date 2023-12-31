package org.rent.circle.vendor.api.service.mapper;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.rent.circle.vendor.api.dto.SaveVendorDto;
import org.rent.circle.vendor.api.dto.UpdateVendorDto;
import org.rent.circle.vendor.api.dto.VendorDto;
import org.rent.circle.vendor.api.persistence.model.Vendor;
import org.rent.circle.vendor.api.persistence.model.Worker;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface VendorMapper {

    Vendor toModel(SaveVendorDto saveVendor);

    void updateVendor(UpdateVendorDto updateVendorInfo, @MappingTarget Vendor vendor);

    @Mapping(target = "workers", source = "workers")
    VendorDto toDto(Vendor vendor);

    @Mapping(target = "workers", source = "workers")
    List<VendorDto> toDtoList(List<Vendor> vendors);

    @AfterMapping
    default void afterMapping(@MappingTarget Vendor target) {
        List<Worker> workers = target.getWorkers();
        if (workers == null || workers.isEmpty()) {
            return;
        }

        for (Worker worker : workers) {
            worker.setVendor(target);
        }
    }
}
