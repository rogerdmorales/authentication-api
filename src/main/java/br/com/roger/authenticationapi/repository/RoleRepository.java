package br.com.roger.authenticationapi.repository;

import br.com.roger.authenticationapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository< Role, Long > {
}
