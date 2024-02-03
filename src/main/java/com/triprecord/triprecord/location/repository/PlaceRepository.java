package com.triprecord.triprecord.location.repository;


import com.triprecord.triprecord.location.entity.Country;
import com.triprecord.triprecord.location.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByPlaceId(Long placeId);
    List<Place> findAllByPlaceCountry(Country country);
}
