package org.rent.circle.vendor.api.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.rent.circle.vendor.api.persistence.BaseModel;

@Entity
@Table(name = "worker")
@Setter
@Getter
@ToString
public class Worker extends BaseModel {

    @Id
    private Long id;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
