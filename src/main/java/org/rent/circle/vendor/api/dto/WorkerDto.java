package org.rent.circle.vendor.api.dto;


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
public class WorkerDto {

    private String name;
    private String email;
    private String phone;
    private Boolean active;
}
