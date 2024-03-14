package org.cardealerexerice.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountsRootDto implements Serializable {

    @XmlElement(name = "sale")
    private List<SaleDiscountDto> saleDiscountDtos;

    public List<SaleDiscountDto> getSaleDiscountDtos() {
        return saleDiscountDtos;
    }

    public void setSaleDiscountDtos(List<SaleDiscountDto> saleDiscountDtos) {
        this.saleDiscountDtos = saleDiscountDtos;
    }
}
