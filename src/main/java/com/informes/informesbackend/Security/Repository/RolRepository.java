package com.informes.informesbackend.Security.Repository;


import com.informes.informesbackend.Security.Entity.Rol;
import com.informes.informesbackend.Security.Enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
