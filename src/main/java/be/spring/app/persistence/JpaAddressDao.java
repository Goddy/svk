package be.spring.app.persistence;

import be.spring.app.interfaces.AddressDao;
import be.spring.app.model.Address;
import org.springframework.stereotype.Repository;

/**
 * Created by u0090265 on 9/11/14.
 */
@Repository
public class JpaAddressDao extends AbstractJpaDao <Address> implements AddressDao{
}
