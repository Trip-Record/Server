package com.triprecord.triprecord.location.repository;

import com.triprecord.triprecord.location.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    List<Continent> findAll();

}
