package com.spboot.parcialcompiladores.owner.service;

import com.spboot.parcialcompiladores.owner.dto.OwnerUpsertDTO;
import com.spboot.parcialcompiladores.owner.model.OwnerProfile;
import com.spboot.parcialcompiladores.owner.repository.OwnerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerProfileService {

    private final OwnerProfileRepository repo;

    public OwnerProfileService(OwnerProfileRepository repo) {
        this.repo = repo;
    }

    public OwnerProfile get() {
        return repo.findById(1L).orElse(null);
    }

    public OwnerProfile upsert(OwnerUpsertDTO dto) {
        OwnerProfile o = repo.findById(1L).orElse(new OwnerProfile());
        o.setId(1L);
        o.setName(dto.name());
        o.setAlias(dto.alias());
        o.setBio(dto.bio());
        o.setBackstory(dto.backstory());
        return repo.save(o);
    }
}