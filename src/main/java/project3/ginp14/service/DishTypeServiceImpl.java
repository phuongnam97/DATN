package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.DishTypeDao;
import project3.ginp14.entity.DishType;

import java.util.List;

@Service
public class DishTypeServiceImpl implements DishTypeService{
    @Autowired
    private DishTypeDao dishTypeDao;

    @Override
    public List<DishType> findAll() {
        List<DishType> list = dishTypeDao.findAll();
        return list;
    }

    @Override
    public Page<DishType> findAll(Pageable pageable){
        return dishTypeDao.findAll(pageable);
    }

    @Override
    public void save(DishType dishType) {
        dishTypeDao.save(dishType);
    }

    @Override
    public DishType findById(int id) {
        return dishTypeDao.findById(id);
    }

    @Override
    public void deleteById(int id) {
        dishTypeDao.deleteById(id);
    }
}
