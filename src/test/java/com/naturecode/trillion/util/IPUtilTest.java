package com.naturecode.trillion.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.naturecode.trillion.exception.IPException;
import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.repository.IPRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IPUtilTest {
  private IPUtil ipUtil;

  @Test
  public void getIpsFromCIDR() {
    IPModel ipModelMock = new IPModel("10.10.0.0/28");
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    ipUtil = new IPUtil(ipRepositoryMock);
    List<IPModel> result = ipUtil.getIpsFromCIDR(ipModelMock);
    assertEquals(result.size(), 16);
  }

  @Test
  public void overlapCIDR_Contains_NetworkAddress_True() throws IPException {
    IPModel ipModelMock = new IPModel("10.10.0.0/28");
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.0", "available"));
    mockIPList.add(new IPModel("10.10.0.1", "acquired"));
    
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    ipUtil = new IPUtil(ipRepositoryMock);
    Mockito.when(ipRepositoryMock.findAll()).thenReturn(mockIPList);
    boolean result = ipUtil.overlapCIDR(ipModelMock);
    assertEquals(result, true);
  }

  @Test
  public void overlapCIDR_Contains_BroadcastAddress_True() throws IPException {
    IPModel ipModelMock = new IPModel("10.10.0.0/28");
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.0", "available"));
    mockIPList.add(new IPModel("10.10.0.15", "acquired"));
    
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    ipUtil = new IPUtil(ipRepositoryMock);
    Mockito.when(ipRepositoryMock.findAll()).thenReturn(mockIPList);
    boolean result = ipUtil.overlapCIDR(ipModelMock);
    assertEquals(result, true);
  }

  @Test
  public void overlapCIDR_False() throws IPException {
    IPModel ipModelMock = new IPModel("10.10.0.0/28");
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.16", "available"));
    mockIPList.add(new IPModel("10.10.0.17", "acquired"));
    
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    ipUtil = new IPUtil(ipRepositoryMock);
    Mockito.when(ipRepositoryMock.findAll()).thenReturn(mockIPList);
    boolean result = ipUtil.overlapCIDR(ipModelMock);
    assertEquals(result, false);
  }

  @Test
  public void overlapCIDR_IllegalArgumentException() throws IPException {
    IPModel ipModelMock = new IPModel("10.10.0.s");
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    ipUtil = new IPUtil(ipRepositoryMock);
    Assertions.assertThrows(IPException.class, () -> ipUtil.overlapCIDR(ipModelMock));
  }
}