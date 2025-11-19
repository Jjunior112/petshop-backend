package com.littlebirds.petshop.domain.models;
import com.littlebirds.petshop.domain.dtos.address.AddressRegisterDto;
import com.littlebirds.petshop.domain.dtos.user.UserUpdateDto;
import com.littlebirds.petshop.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    boolean isActive = true;

    public User(String fullName, String email, String password, String phone, UserRole role, AddressRegisterDto registerDto) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.address = (registerDto != null) ? new Address(registerDto) : new Address();
    }

    public void inactiveUser() {

        if (isActive) {
            this.isActive = false;
        }
    }

    public void EditUserInfo(UserUpdateDto updateDto)
    {
        if(updateDto.fullName()!=null)
        {
            this.fullName = updateDto.fullName();
        }

        if(updateDto.phone()!=null)
        {
            this.phone = updateDto.phone();
        }

        if(updateDto.addressRegisterDto()!=null)
        {
            this.address.updateInformation(updateDto.addressRegisterDto());
        }

    }

    public void reactiveUser() {

        if (!isActive) {
            this.isActive = true;
        }
    }

    public Address getAddress()
    {
        if(this.address==null)
        {
            return null;
        }
        return this.address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}