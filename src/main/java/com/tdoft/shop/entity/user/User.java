package com.tdoft.shop.entity.user;

import com.tdoft.shop.entity.Good;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Role role;
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressDetails addressDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private ContactInfo contactInfo;

    @OneToMany(orphanRemoval = true)
    private Set<Good> likedGoods;

    @OneToMany(orphanRemoval = true)
    private List<Good> orderedGoods;

    @OneToMany(orphanRemoval = true)
    private Set<Good> selectedGoods;

    {
        role = Role.USER;
        status = Status.ACTIVE;
        likedGoods = new HashSet<>();
        orderedGoods = new ArrayList<>();
        selectedGoods = new HashSet<>();
    }

}
