package org.rent.circle.vendor.api.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
    private List<Worker> workers;
}
