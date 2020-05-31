package com.naturecode.trillion.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Component
public class IPResult {
  private int status;
  private String message;
}