package org.ebuy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Entity
@Getter
@Setter
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttributeValue attributeValue = (AttributeValue) obj;
        return Objects.equals(id, attributeValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
