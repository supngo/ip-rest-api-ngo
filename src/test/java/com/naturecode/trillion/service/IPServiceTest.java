package com.naturecode.trillion.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.naturecode.trillion.exception.IPException;
import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.repository.IPRepository;
import com.naturecode.trillion.util.IPUtil;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IPServiceTest {
  private IPService ipService;

  @Test
  public void getAllIPsSuccess() {
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    IPUtil ipUtilMock = Mockito.mock(IPUtil.class);
    ipService = new IPService(ipRepositoryMock, ipUtilMock);
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.0", "available"));
    mockIPList.add(new IPModel("10.10.0.1", "acquired"));
    Mockito.when(ipRepositoryMock.findAll()).thenReturn(mockIPList);

    List<IPModel> result = ipService.getAllIPs();
    assertEquals(result.size(), mockIPList.size());
  }

  @Test
  public void createIPSuccess() throws IPException {
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.0", "available"));
    mockIPList.add(new IPModel("10.10.0.1", "acquired"));

    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    IPUtil ipUtilMock = Mockito.mock(IPUtil.class);
    ipService = new IPService(ipRepositoryMock, ipUtilMock);

    IPModel ipModelMock = new IPModel("10.10.0.0/28");
    Mockito.when(ipUtilMock.overlapCIDR(ipModelMock)).thenReturn(false);
    Mockito.when(ipRepositoryMock.saveAll(ArgumentMatchers.anyIterable())).thenReturn(mockIPList);
    ipService.createIP(ipModelMock);
    verify(ipUtilMock, times(1)).overlapCIDR(ipModelMock);
  }

  @Test
  public void acquireTest() throws IPException {
    IPModel ipResultMock = new IPModel("10.10.0.0", "acquired");
    IPRepository ipRepositoryMock = Mockito.mock(IPRepository.class);
    IPUtil ipUtilMock = Mockito.mock(IPUtil.class);
    ipService = new IPService(ipRepositoryMock, ipUtilMock);
    Mockito.when(ipRepositoryMock.findByIP(ArgumentMatchers.anyString())).thenReturn(Optional.of(ipResultMock));
    IPModel result = ipService.updateIP(ipResultMock, "acquired");
    assertEquals(result.getStatus(), "acquired");
  }
}