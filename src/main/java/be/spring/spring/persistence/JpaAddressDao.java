package be.spring.spring.persistence;

import be.spring.spring.interfaces.AddressDao;
import be.spring.spring.model.Address;
import org.springframework.stereotype.Repository;

/**
 * Created by u0090265 on 9/11/14.
 */
@Repository
public class JpaAddressDao extends AbstractJpaDao <Address> implements AddressDao{
}
