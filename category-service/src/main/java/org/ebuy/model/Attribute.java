package org.ebuy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Entity
@Getter
@Setter
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "attribute_id")
    private Set<AttributeValue> attributeValues = new HashSet<>();

    public void addAttributeValue(AttributeValue attributeValue) {
        attributeValues.add(attributeValue);
    }

    public void removeAttributeValue(AttributeValue attributeValue) {
        attributeValues.remove(attributeValue);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attribute attribute = (Attribute) obj;
        return Objects.equals(id, attribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
