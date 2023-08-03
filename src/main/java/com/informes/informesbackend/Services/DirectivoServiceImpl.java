package com.informes.informesbackend.Services;
import com.informes.informesbackend.Models.Entities.Directivo;
import com.informes.informesbackend.Repositories.DirectivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class DirectivoServiceImpl implements DirectivoService{

    @Autowired
    DirectivoRepository repository;
    @Override
    public List<Directivo> listar() {
        return repository.findAll();
    }

    @Override
    public Optional<Directivo> listarporId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Directivo> listarporDni(String id) {
        return repository.findByDni(id);
    }

    @Override
    public Directivo guardar(Directivo admin) {
        return repository.save(admin);
    }

    @Override
    public void eliminar(Long id) {
          repository.deleteById(id);
    }

    @Override
    public Optional<Directivo> porEmail(String email) {
        return repository.findByEmail(email);
    }
}
