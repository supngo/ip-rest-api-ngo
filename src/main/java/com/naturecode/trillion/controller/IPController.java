package com.naturecode.trillion.controller;

import java.util.List;

import com.naturecode.trillion.exception.IPException;
import com.naturecode.trillion.model.IPResult;
import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.service.IPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/ip_management")
public class IPController {
  private IPService ipService;

  @Autowired
  public IPController(IPService ipService) {
    this.ipService = ipService;
  }

  @GetMapping("/get")
  public ResponseEntity<?> getAllIPs(){
    try {
      List<IPModel> ipList =  ipService.getAllIPs();
      return ResponseEntity.ok(ipList);
    } catch (Exception e) {
      log.error("Exception in getAllIPs(): {}", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(500, "Server Error"));
    }
  }

  @PostMapping("/create")
  public ResponseEntity<?> createIP(@RequestBody IPModel cidr) {
    try {
      ipService.createIP(cidr);
      log.info("CIDR Block {} is created", cidr.getIp());
      return ResponseEntity.ok(new IPResult(200, "Success"));
    } catch (IPException e) {
      log.error("IPException in createIP(): {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(400, e.getMessage()));
    }
    catch (Exception e) {
      log.error("Exception in createIP(): {}", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(500, "Server Error"));
    }
  }

  @PutMapping("/acquire")
  public ResponseEntity<?> acquireIP(@RequestBody IPModel ip) {
    try { 
      IPModel ipModel = ipService.updateIP(ip, "acquired");
      log.info("IP {} is {}", ipModel.getIp(), ipModel.getStatus());
      return ResponseEntity.ok(ipModel);
    } catch (IPException e) {
      log.error("IPException in acquireIP(): {} {}", ip.getIp(), e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(400, e.getMessage()));
    } catch (Exception e) {
      log.error("Exception in acquireIP(): {}", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(500, "Server Error"));
    }
  }

  @PutMapping("/release")
  public ResponseEntity<?> releaseIP(@RequestBody IPModel ip) {
    try {
      IPModel ipModel = ipService.updateIP(ip, "available");
      log.info("IP {} is {}", ipModel.getIp(), ipModel.getStatus());
      return ResponseEntity.ok(ipModel);
    } catch (IPException e) {
      log.error("IPException in releaseIP(): {} {}", ip.getIp(), e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(400, e.getMessage()));
    } catch (Exception e) {
      log.error("Exception in releaseIP(): {}", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IPResult(500, "Server Error"));
    }
  }
}