package com.naturecode.trillion.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "ip_management")
@Getter
@Setter
@NoArgsConstructor
public class IPModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long id;

  private String ip;
  private String status;

  public IPModel(String ip, String status) {
    this.ip = ip;
    this.status = status;
  }

  public IPModel(String ip) {
    this.ip = ip;
  }
}