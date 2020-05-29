package com.naturecode.trillion.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.naturecode.trillion.model.IPModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface IPRepository extends JpaRepository<IPModel, Long> {

  @Query("SELECT t FROM IPModel t WHERE t.ip = ?1")
  Optional<IPModel> findByIP(String ip);
}