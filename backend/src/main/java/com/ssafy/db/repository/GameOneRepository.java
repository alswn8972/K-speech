package com.ssafy.db.repository;

import com.ssafy.db.entity.GameOne;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameOneRepository extends JpaRepository<GameOne, Long> {
}
