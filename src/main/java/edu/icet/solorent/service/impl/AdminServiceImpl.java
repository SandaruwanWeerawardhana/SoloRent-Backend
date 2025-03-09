package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.repository.AdminRepository;
import edu.icet.solorent.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final ModelMapper mapper;


    @Override
    public void add(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
    }

    @Override
    public Optional<AdminEntity> searchById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Admin> getAll() {
        List<AdminEntity> entity = repository.findAll();
        List<Admin> adminList = new ArrayList<>();
        entity.forEach(e->mapper.map(e, AdminEntity.class));
        return adminList;
    }
}
