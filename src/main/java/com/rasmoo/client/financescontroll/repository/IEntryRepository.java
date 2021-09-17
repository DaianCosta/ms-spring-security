package com.rasmoo.client.financescontroll.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rasmoo.client.financescontroll.entity.Entry;

@Repository
public interface IEntryRepository  extends JpaRepository<Entry, Serializable>{
	
	@Query("SELECT e FROM Entry e where e.user.id = :userId")
	public List<Entry> findAllByUserId(@Param("userId")Long userId);
	
}
