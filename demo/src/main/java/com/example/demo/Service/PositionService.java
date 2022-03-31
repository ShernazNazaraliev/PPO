package com.example.demo.Service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public void delete(Long id) throws Exception {
        try {
            positionRepository.deleteById(id);
        }
        catch (Exception exception){
            throw new Exception("Конфлик при удалении");
        }
    }

    public void create(Position position) {
        positionRepository.save(position);
    }

    public void update(Long id, Position position) {
        Position positionUpdate = positionRepository.getById(id);
        positionUpdate.setName(position.getName());
        positionRepository.save(positionUpdate);
    }
}
