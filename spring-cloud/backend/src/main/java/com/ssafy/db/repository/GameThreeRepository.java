package com.ssafy.db.repository;

import com.ssafy.db.entity.GameThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameThreeRepository extends JpaRepository<GameThree, Long> {
}
