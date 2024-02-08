package com.triprecord.triprecord.record.repository;

import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.user.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordPlaceRepository extends JpaRepository<RecordPlace, Long> {

    long countByLinkedRecord(Record linkedRecord);
    void deleteByLinkedRecordAndRecordPlace(Record linkedRecord, Place recordPlace);
    List<RecordPlace> findAll();

    @Query("SELECT r.recordPlace FROM RecordPlace r WHERE r.linkedRecord = :record")
    List<Place> findAllPlaceByLinkedRecord(Record record);

    @Query("select count(distinct rp.recordPlace) from RecordPlace rp where rp.linkedRecord.createdUser = :user")
    Long placeCount(@Param("user") User user);

    @Query(value = "select count(rp.recordPlace) from RecordPlace rp"
            + " where rp.recordPlace.placeId = :pid and function('date_format',rp.linkedRecord.tripStartDate,'%Y-%m') = :date "
            + "group by rp.recordPlace")
    Optional<Integer> getCount(@Param("pid") Long placeId, @Param("date") String date);

    @Query(value = "select r.ranking from"
            + "(select dense_rank() over (order by count(rp.place_id) desc ) as ranking, rp.place_id "
            + "from record_place rp LEFT JOIN record re ON rp.record_id = re.record_id where DATE_FORMAT(trip_start_date, '%Y-%m') = :date "
            + "group by rp.place_id) r where r.place_id = :pid and r.ranking <= 7 ", nativeQuery = true)
    Optional<Integer> getRank(@Param("pid") Long placeId, @Param("date") String date);

    @Query("SELECT COUNT(rp.recordPlace) " +
            "FROM RecordPlace rp " +
            "WHERE ((function('date_format',rp.linkedRecord.tripStartDate,'%Y-%m') between :startDate and :middleDate or function('date_format',rp.linkedRecord.tripStartDate,'%Y-%m') = :endDate)) and rp.recordPlace.placeId = :pid " +
            "GROUP BY rp.recordPlace")
    Optional<Integer> getCountsBySeason(@Param("startDate") String startDate, @Param("middleDate") String middleDate, @Param("endDate") String endDate, @Param("pid") Long placeId);

    @Query(value = "SELECT r.ranking FROM (" +
            "    SELECT rank() over (order by count(rp.place_id) desc ) as ranking, rp.place_id "
            + "FROM record_place rp LEFT JOIN record re ON rp.record_id = re.record_id WHERE DATE_FORMAT(trip_start_date, '%Y-%m') between :startDate and :middleDate or DATE_FORMAT(trip_start_date, '%Y-%m') = :endDate " +
            " GROUP BY rp.place_id) r WHERE r.place_id = :pid and r.ranking <= 7", nativeQuery = true)
    Optional<Integer> getRanksBySeason(@Param("startDate") String startDate, @Param("middleDate") String middleDate, @Param("endDate") String endDate, @Param("pid") Long placeId);

}
