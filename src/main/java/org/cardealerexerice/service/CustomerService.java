package org.cardealerexerice.service;

import jakarta.xml.bind.JAXBException;

public interface CustomerService {

    void seedCustomers() throws JAXBException;

    void exportOrderedCustomers() throws JAXBException;

    void exportCustomersWithBoughtCars() throws JAXBException;
}
