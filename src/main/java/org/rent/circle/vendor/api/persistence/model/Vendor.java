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
@Table(name = "vendor")
@Setter
@Getter
@ToString
public class Vendor extends BaseModel {

    @Id
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
