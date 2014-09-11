package be.spring.spring.service;

import be.spring.spring.interfaces.AddressDao;
import be.spring.spring.interfaces.AddressService;
import be.spring.spring.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by u0090265 on 9/11/14.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.getAll();
    }

    @Override
    public Address getAddressById(String id) {
        return addressDao.get(id);
    }
}
