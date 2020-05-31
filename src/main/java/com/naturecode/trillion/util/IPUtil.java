package com.naturecode.trillion.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.naturecode.trillion.exception.IPException;
import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.repository.IPRepository;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IPUtil {
  @Autowired
  private IPRepository ipRepository;

  @Autowired
  public IPUtil(IPRepository ipRepository) {
    this.ipRepository = ipRepository;
  }

  public List<IPModel> getIpsFromCIDR(IPModel cidr){
    SubnetUtils utils = new SubnetUtils(cidr.getIp());
    String[] addresses = utils.getInfo().getAllAddresses();
    List<IPModel> allIps = new ArrayList<IPModel>();
    allIps.add(new IPModel(utils.getInfo().getNetworkAddress()));
    Arrays.stream(addresses).forEach(ip -> allIps.add(new IPModel(ip)));
    allIps.add(new IPModel(utils.getInfo().getBroadcastAddress()));
    return allIps;
  }

  public boolean overlapCIDR(IPModel cidr) throws IPException {
    try {
      SubnetUtils ipUtils = new SubnetUtils(cidr.getIp());
      List<IPModel> currentIPList = ipRepository.findAll();
      List<IPModel> overlapIps = currentIPList.stream().filter(ip -> {
        return ipUtils.getInfo().isInRange(ip.getIp()) 
        || ip.getIp().equals(ipUtils.getInfo().getNetworkAddress()) 
        || ip.getIp().equals(ipUtils.getInfo().getBroadcastAddress());
      }).collect(Collectors.toList());
      return (overlapIps.size() > 0);
    } catch (IllegalArgumentException e) {
        throw new IPException("CIDR Block Invalid");
    }
  }
}