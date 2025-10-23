package com.littlebirds.petshop.infra.repositories;

import com.littlebirds.petshop.domain.enums.UserRole;
import com.littlebirds.petshop.domain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String login);

    Page<User> findByRole(UserRole role, Pageable pageable);

    @Query(
            """
                    select u.isActive from users u
                    where u.id = :id
                    """
    )
    Boolean findIsActiveById(UUID id);

}