package com.naturecode.trillion.service;

import java.util.List;
import java.util.stream.Collectors;

import com.naturecode.trillion.exception.IPException;
import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.repository.IPRepository;
import com.naturecode.trillion.util.IPUtil;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IPService {
  private IPRepository ipRepository;
  private IPUtil ipUtil;

  @Autowired
  public IPService(IPRepository ipRepository, IPUtil ipUtil) {
    this.ipRepository = ipRepository;
    this.ipUtil = ipUtil;
  }

  public List<IPModel> getAllIPs() {
    List<IPModel> ipList = ipRepository.findAll();
    return ipList.stream().map(ip -> { 
      return new IPModel(ip.getIp(), ip.getStatus());
    }).collect(Collectors.toList());
  }

  public void createIP(IPModel cidr) throws IPException {
    try {
      String status = "available";
      if (ipUtil.overlapCIDR(cidr)) {
        log.error("CIDR {} is overlapped with current IPS", cidr.getIp());
        throw new IPException("CIDR Block Overlapped");
      }
      List<IPModel> allIps = ipUtil.getIpsFromCIDR(cidr);
      allIps.stream().forEach(ip -> ip.setStatus(status));
      ipRepository.saveAll(allIps);
    } catch (IllegalArgumentException e) {
      log.error("IllegalArgumentException in createIP(): {}", e.getMessage());
      throw new IPException("CIDR Block Invalid");
    }
  }

  public IPModel updateIP(IPModel ipModel, String status) throws IPException {
    if (!InetAddressValidator.getInstance().isValid(ipModel.getIp())) {
      throw new IPException("IP Invalid");
    }
    IPModel ip = ipRepository.findByIP(ipModel.getIp( )).orElseThrow(() -> new IPException("IP Not Found"));
    ip.setStatus(status);
    ipRepository.save(ip);
    ip.setId(null);
    return ip;
  }
}