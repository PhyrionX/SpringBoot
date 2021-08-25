package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
	Game findByName(Game name);
}
