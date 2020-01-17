package com.greatDeal.greatDeal.repository;

import com.greatDeal.greatDeal.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findRoleByName(String name);

}
