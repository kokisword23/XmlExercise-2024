package org.cardealerexerice.data.repositories;

import org.cardealerexerice.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Set<Customer> findAllByOrderByBirthDateAscIsYoungDriverAsc();

    @Query(value = "FROM Customer WHERE SIZE(sales) > 0")
    Set<Customer> findAllWithBoughtCars();
}
