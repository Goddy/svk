package be.spring.app.service;

import be.spring.app.model.Address;

import java.util.List;

/**
 * Created by u0090265 on 9/11/14.
 */
public interface AddressService {
    List<Address> getAllAddresses();

    Address getAddressById(String id);
}
