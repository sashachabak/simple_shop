package com.tdoft.shop.repository;

import com.tdoft.shop.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Page<Good> findAll(Pageable pageable);

}
