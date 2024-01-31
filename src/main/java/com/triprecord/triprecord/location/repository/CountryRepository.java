package com.triprecord.triprecord.location.repository;

import com.triprecord.triprecord.location.entity.Continent;
import com.triprecord.triprecord.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findCountryByContinent(Continent continent);
}
