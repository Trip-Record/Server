package com.triprecord.triprecord.record.repository;


import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Long countRecordByCreatedUser(User user);

    Optional<Record> findByRecordId(Long recordId);

    @Query("SELECT r FROM Record r ORDER BY r.recordId DESC ")
    Page<Record> findAllOrderById(Pageable pageable);

    Page<Record> findAllByCreatedUser(User user, Pageable pageable);

}
