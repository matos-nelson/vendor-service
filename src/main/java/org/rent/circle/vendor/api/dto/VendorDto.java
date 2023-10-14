package org.rent.circle.vendor.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto {

    private Long ownerId;
    private Long addressId;
    private String name;
    private String email;
    private String phone;
    private List<WorkerDto> workers;
}
