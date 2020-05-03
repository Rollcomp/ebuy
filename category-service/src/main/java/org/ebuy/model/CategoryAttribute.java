package org.ebuy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Entity
@Getter
@Setter
public class CategoryAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean required;
    private boolean allowCustom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CategoryAttribute categoryAttribute = (CategoryAttribute) obj;
        return Objects.equals(id, categoryAttribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
