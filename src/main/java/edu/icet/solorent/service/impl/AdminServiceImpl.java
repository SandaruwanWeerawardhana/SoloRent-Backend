package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Admin;
import edu.icet.solorent.entity.AdminEntity;
import edu.icet.solorent.repository.AdminRepository;
import edu.icet.solorent.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final ModelMapper mapper;

    @Override
    public AdminEntity add(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Admin admin) {
        AdminEntity existingEntity = repository.findById(admin.getAdminID())
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
        AdminEntity updatedEntity = mapper.map(admin, AdminEntity.class);
        updatedEntity.setAdminID(existingEntity.getAdminID());
        repository.save(updatedEntity);
    }

    @Override
    public Admin searchById(Long id) {
        return mapper.map(repository.findById((id)), Admin.class);
    }

    @Override
    public List<Admin> getAll() {
        List<AdminEntity> entity = repository.findAll();
        List<Admin> arrayList = new ArrayList<>();
        entity.forEach(e -> arrayList.add(mapper.map(e, Admin.class)));
        return arrayList;
    }
}
