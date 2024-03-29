package com.triprecord.triprecord.user.repository;

import com.triprecord.triprecord.user.entity.TripStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripStyleRepository extends JpaRepository<TripStyle, Long> {
    List<TripStyle> findAll();
}
