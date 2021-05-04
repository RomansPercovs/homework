package homework.model;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class CustomerDebt {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double amount;
    private String currency;
    private DateTime dueDate;
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDebt debt = (CustomerDebt) o;
        return id.equals(debt.id) &&
                amount.equals(debt.amount) &&
                currency.equals(debt.currency) &&
                dueDate.equals(debt.dueDate) &&
                customer.equals(debt.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, dueDate, customer);
    }
}
