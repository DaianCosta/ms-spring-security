package com.rasmoo.client.financescontroll.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rasmoo.client.financescontroll.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Serializable>{
	
	@Query("SELECT u FROM User u WHERE u.credencial.email = :email")
	Optional<User> findByEmail(@Param("email")String email);

}
