package org.rent.circle.vendor.api.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.rent.circle.vendor.api.dto.SaveWorkerDto;
import org.rent.circle.vendor.api.dto.UpdateWorkerDto;
import org.rent.circle.vendor.api.persistence.model.Worker;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "cdi")
public interface WorkerMapper {

    Worker toModel(SaveWorkerDto saveWorker);

    void updateWorker(UpdateWorkerDto updateWorkerInfo, @MappingTarget Worker worker);
}
