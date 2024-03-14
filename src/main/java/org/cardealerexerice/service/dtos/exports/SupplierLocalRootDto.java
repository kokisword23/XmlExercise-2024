package org.cardealerexerice.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalRootDto implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierLocalDto> supplierLocalDto;

    public List<SupplierLocalDto> getSupplierLocalDto() {
        return supplierLocalDto;
    }

    public void setSupplierLocalDto(List<SupplierLocalDto> supplierLocalDto) {
        this.supplierLocalDto = supplierLocalDto;
    }
}
