package com.beyou.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beyou.common.entity.Role;

/*the annotation @Repository for this interface.
And we have this interface extends the CrudRepository interface provided by Spring Data JPA here
with the type parameter T is for the entity type, which is Role*/
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
