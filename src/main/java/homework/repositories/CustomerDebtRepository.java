package homework.repositories;

import homework.model.CustomerDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDebtRepository extends JpaRepository<CustomerDebt, Long> {
        List<CustomerDebt> findAllByCustomerId(Long customerId);
}
