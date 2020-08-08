package org.rawin.allianz.exam1.repository;

import org.rawin.allianz.exam1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
