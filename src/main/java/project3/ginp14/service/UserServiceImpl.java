package project3.ginp14.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project3.ginp14.dao.RoleDao;
import project3.ginp14.dao.UserDao;
import project3.ginp14.entity.Role;
import project3.ginp14.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public User findUserbyUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean checkExistInfo(String username, String email, String telephone) {
        User user = userDao.findByUsernameAndEmailAndTelephone(username, email, telephone);
        if (user == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        System.out.println(user.getPassword());
        userDao.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null){ throw new UsernameNotFoundException("Username not found");}
        return user;
    }

    @Override
    public void blockUser(int id){
        User user = userDao.findById(id);
        if (user != null){
            user.setStatus(2);
            userDao.save(user);
        }
    }

    @Override
    public void unblockUser(int id){
        User user = userDao.findById(id);
        if (user != null){
            user.setStatus(1);
            userDao.save(user);
        }
    }

    @Override
    public List<User> findAllByRoleIsNotAdmin() {
        return userDao.findAllByRoleIsNotAdmin(3);
    }

    @Override
    public Page<User> findAllByRoleIsNotAdmin(Pageable pageable) {
        return userDao.findAll(pageable);
    }
}
