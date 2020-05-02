package org.ebuy.model.category;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private Set<Category> subCategories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeautifiedName() {
        return beautifiedName;
    }

    public void setBeautifiedName(String beautifiedName) {
        this.beautifiedName = beautifiedName;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    public void removeSubCategory(Category subCategory) {
        subCategories.remove(subCategory);

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
