package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.RestaurantTypeDao;
import project3.ginp14.entity.RestaurantType;

import java.util.List;

@Service
public class RestaurantTypeServiceImpl implements RestaurantTypeService {
    @Autowired
    private RestaurantTypeDao restaurantTypeDao;

    @Override
    public List<RestaurantType> findAll(){
        return restaurantTypeDao.findAll();
    }

    @Override
    public void save(RestaurantType restaurantType) {
        restaurantTypeDao.save(restaurantType);
    }

    @Override
    public RestaurantType findById(int id) {
        return restaurantTypeDao.findById(id);
    }

    @Override
    public void deleteById(int id) {
        restaurantTypeDao.deleteById(id);
    }

    @Override
    public Page<RestaurantType> findAll(Pageable pageable) {
        return restaurantTypeDao.findAll(pageable);
    }
}
