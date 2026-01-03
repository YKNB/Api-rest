package com.quest.etna.repositories;

import com.quest.etna.model.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    /*
	@Query ("SELECT a FROM Address a WHERE a.id=:id" )
    List<Address> findAllForUserId(int id);
	*/
    @Query("SELECT a FROM Address a WHERE a.user.id = :userId")
    List<Address> findAllForUserId(@Param("userId") int userId);

    Address findAddressById(int id);
    List<Address> findAll();

    @Modifying
    @Query(value = "DELETE FROM address WHERE id = :addressId", nativeQuery = true)
    void deleteAddressById(@Param("addressId") int addressId);
}
