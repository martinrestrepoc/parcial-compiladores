package com.spboot.parcialcompiladores.fighters.model;
import jakarta.persistence.*;

@Entity
@Table(name = "fighters")

public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int strength;
    private int speed;
    private int stamina;
    private String specialSkill;

    @Column(length = 2000)
    private String bio;

    @Column(length = 4000)
    private String backstory;

    private int wins;
    private int losses;

    public Fighter(){
    }

    public Fighter(String name, int strength, int speed, int stamina, String specialSkill, String bio, String backstory, int wins, int losses) {
        this.name = name;
        this.strength = strength;
        this.speed = speed;
        this.stamina = stamina;
        this.specialSkill = specialSkill;
        this.bio = bio;
        this.backstory = backstory;
        this.wins = wins;
        this.losses = losses;
    }

    // getters/setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = stamina; }

    public String getSpecialSkill() { return specialSkill; }
    public void setSpecialSkill(String specialSkill) { this.specialSkill = specialSkill; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getBackstory() { return backstory; }
    public void setBackstory(String backstory) { this.backstory = backstory; }

    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }

    public int getLosses() { return losses; }
    public void setLosses(int losses) { this.losses = losses; }
}
