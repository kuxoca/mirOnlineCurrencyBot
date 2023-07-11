package com.kuxoca.mironline.repo;

import com.kuxoca.mironline.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    @Query(value = "select distinct u.name from Currency u")
    List<String> findDistinctByName();

    @Query(value = "select g.currency from Currency g where (g.localDateTime = (select max(u.localDateTime) from Currency u where u.name = :name) and g.name = :name)")
    BigDecimal findLastDataByName(@Param("name") String name);

}
