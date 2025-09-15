package com.arogyam.health.repository;

import com.arogyam.health.entity.VillageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<VillageEntity, Long> {
    Optional<VillageEntity> findByNameAndDistrict(String name, String district);

    List<VillageEntity> findByDistrict(String district);

    List<VillageEntity> findByState(String state);

    @Query("SELECT v FROM VillageEntity v WHERE v.district = :district ORDER BY v.name")
    List<VillageEntity> findByDistrictOrderByName(@Param("district") String district);

    boolean existsByNameAndDistrict(String name, String district);

}
