package com.spboot.parcialcompiladores.fights.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spboot.parcialcompiladores.fighters.model.Fighter;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "fights")
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fighter1_id", nullable = false)
    private Fighter fighter1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fighter2_id", nullable = false)
    private Fighter fighter2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FightStatus status;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    @JsonIgnore
    private Fighter winner;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant executedAt;

    @Column(length = 2000)
    private String resultSummary;

    public Fight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fighter getFighter1() {
        return fighter1;
    }

    public void setFighter1(Fighter fighter1) {
        this.fighter1 = fighter1;
    }

    public Fighter getFighter2() {
        return fighter2;
    }

    public void setFighter2(Fighter fighter2) {
        this.fighter2 = fighter2;
    }

    public FightStatus getStatus() {
        return status;
    }

    public void setStatus(FightStatus status) {
        this.status = status;
    }

    public Fighter getWinner() {
        return winner;
    }

    public void setWinner(Fighter winner) {
        this.winner = winner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Instant executedAt) {
        this.executedAt = executedAt;
    }

    public String getResultSummary() {
        return resultSummary;
    }

    public void setResultSummary(String resultSummary) {
        this.resultSummary = resultSummary;
    }
}
