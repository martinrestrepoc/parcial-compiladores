package com.spboot.parcialcompiladores.fighters.repository;

import com.spboot.parcialcompiladores.fighters.model.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FighterRepository extends JpaRepository<Fighter, Long> {}
