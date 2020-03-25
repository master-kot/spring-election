package com.nikolay.springmimimimetr.repositories;

import com.nikolay.springmimimimetr.entities.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
}