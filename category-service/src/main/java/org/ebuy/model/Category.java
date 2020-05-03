package org.ebuy.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NaturalId
    private String beautifiedName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Category> subCategories = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "category_id")
    private Set<CategoryAttribute> categoryAttributes = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    public void removeSubCategory(Category subCategory) {
        subCategories.remove(subCategory);
        subCategory.setParent(null);
    }

    public void addCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryAttributes.add(categoryAttribute);
    }

    public void removeCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryAttributes.remove(categoryAttribute);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Category category = (Category) obj;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return (parent == null) ? "/" + name : parent.toString() + "/" + name;
    }

}
