package com.ssafy.db.repository;

import com.ssafy.db.entity.GameTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTwoRepository extends JpaRepository<GameTwo, Long> {
}
