package be.spring.app.service;

import be.spring.app.model.Address;
import be.spring.app.persistence.AddressDao;
import be.spring.app.utils.GeneralUtils;
import com.google.common.collect.Lists;
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
        return Lists.newArrayList(addressDao.findAll());
    }

    @Override
    public Address getAddressById(String id) {
        return addressDao.findOne(GeneralUtils.convertToLong(id));
    }
}
