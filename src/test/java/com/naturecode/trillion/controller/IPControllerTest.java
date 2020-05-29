package com.naturecode.trillion.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.isA;
import java.util.ArrayList;
import java.util.List;

import com.naturecode.trillion.model.IPModel;
import com.naturecode.trillion.service.IPService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = IPController.class)
@AutoConfigureMockMvc
public class IPControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IPService ipServiceMock;

  @Test
  public void getAllIP200Test() throws Exception {
    List<IPModel> mockIPList = new ArrayList<IPModel>();
    mockIPList.add(new IPModel("10.10.0.0", "available"));
    mockIPList.add(new IPModel("10.10.0.1", "acquired"));
    when(ipServiceMock.getAllIPs()).thenReturn(mockIPList);
    mockMvc.perform(MockMvcRequestBuilders.get("/ip_management/get")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("[{\"ip\":\"10.10.0.0\",\"status\":\"available\"},{\"ip\":\"10.10.0.1\",\"status\":\"acquired\"}]")));
  }

  @Test
  public void createIP200Test() throws Exception {
    doNothing().when(ipServiceMock).createIP(isA(IPModel.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/ip_management/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"ip\":\"10.10.0.0/28\"}"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("\"message\":\"Success\"")));
  }

  @Test
  public void acquireIP200Test() throws Exception {
    IPModel ipResultMock = new IPModel("10.10.0.0", "acquired");
    when(ipServiceMock.updateIP(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(ipResultMock);
    mockMvc.perform(MockMvcRequestBuilders.put("/ip_management/acquire")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"ip\":\"10.10.0.0\"}"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("{\"ip\":\"10.10.0.0\",\"status\":\"acquired\"}")));
  }

  @Test
  public void releaseIP200Test() throws Exception {
    IPModel ipResultMock = new IPModel("10.10.0.0", "available");
    when(ipServiceMock.updateIP(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(ipResultMock);
    mockMvc.perform(MockMvcRequestBuilders.put("/ip_management/release")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"ip\":\"10.10.0.0\"}"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("{\"ip\":\"10.10.0.0\",\"status\":\"available\"}")));
  }
}