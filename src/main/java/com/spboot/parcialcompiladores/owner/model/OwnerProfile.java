package com.spboot.parcialcompiladores.owner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "owner_profile")

public class OwnerProfile {
    @Id
    private Long id; //lo fijamos en 1 para que sea único

    @Column(nullable = false)
    private String name; //"Simón Maligno"

    @Column(nullable = false)
    private String alias; //"Don Dinero"

    @Column(length = 2000)
    private String bio;

    @Column(length = 4000)
    private String backstory;

    public OwnerProfile() {
    }

    public OwnerProfile(String name, String alias, String bio, String backstory) {
        this.id = 1L; // ID fijo para asegurar que solo hay un perfil
        this.name = name;
        this.alias = alias;
        this.bio = bio;
        this.backstory = backstory;
    }

    // getters/setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getBackstory() { return backstory; }
    public void setBackstory(String backstory) { this.backstory = backstory; }

}
