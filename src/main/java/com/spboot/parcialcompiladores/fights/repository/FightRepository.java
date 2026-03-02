package com.spboot.parcialcompiladores.fights.repository;

import com.spboot.parcialcompiladores.fights.model.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FightRepository extends JpaRepository<Fight, Long> {
}
