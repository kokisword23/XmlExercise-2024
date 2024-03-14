package org.cardealerexerice.service.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customerSeedDtoList;

    public List<CustomerSeedDto> getCustomerSeedDtoList() {
        return customerSeedDtoList;
    }

    public void setCustomerSeedDtoList(List<CustomerSeedDto> customerSeedDtoList) {
        this.customerSeedDtoList = customerSeedDtoList;
    }
}
