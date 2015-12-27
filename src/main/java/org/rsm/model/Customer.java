package org.rsm.model;

import com.google.common.base.Objects;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;

import static com.google.common.base.Preconditions.checkNotNull;

@XmlRootElement
@Document(collection = "users")
public class Customer {
    @Id
    private String id;
    private String name;

    private Customer(){}

    private Customer(Builder builder) {
        this.id = checkNotNull(builder.id);
        this.name = checkNotNull(builder.name);
    }
    private Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static Builder unbuild(Customer customer) {
        return new Builder().id(customer.getId())
                            .name(customer.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equal(id, customer.id) &&
                Objects.equal(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private String id = RandomStringUtils.randomNumeric(20);
        private String name = StringUtils.EMPTY;

        private Builder(){}
        private Builder(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder id(String id) {
            this.id = checkNotNull(id, "id == NULL");
            return this;
        }

        public Builder name(String name) {
            this.name = checkNotNull(name, "name == NULL");
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
