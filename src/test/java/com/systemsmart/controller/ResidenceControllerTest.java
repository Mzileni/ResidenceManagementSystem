package com.systemsmart.controller;

import com.systemsmart.entity.Residence;
import com.systemsmart.factory.ResidenceFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResidenceControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private String baseURL = "http://localhost:8080/residence/";

    private static Residence residence = ResidenceFactory.createResidence(223, 322, "Res232");

    @Test
    public void a_createResidence() {
        String url = baseURL + "create";
        ResponseEntity<Residence> response = testRestTemplate.postForEntity(url, residence, Residence.class);
        System.out.println("STATUS CODE: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());                               //Body should be the residence we've sent, formatted in json
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(residence.getResidenceId(), response.getBody().getResidenceId());
    }

    @Test
    public void b_updateResidence() {
        String url = baseURL + "update";
        Residence res = new Residence.Builder().copy(residence).setNumberOfRooms(3).setName("RES 3").build();
        ResponseEntity<Residence> response = testRestTemplate.postForEntity(url, res, Residence.class);
        System.out.println("STATUS CODE: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());                              //Body should be the residence we've updated, formatted in json
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(res.getResidenceId(), response.getBody().getResidenceId());
    }

    @Test
    public void c_getResidence() {
        String url = baseURL + residence.getResidenceId();
        ResponseEntity<Residence> response = testRestTemplate.getForEntity(url, Residence.class);
        System.out.println("STATUS CODE: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());                               //Body should be the updated residence, formatted in json
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(residence.getResidenceId(), response.getBody().getResidenceId());
    }

    @Test
    public void d_getAllResidences() {
        String url = baseURL + "all";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("STATUS CODE: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());                               //Body should be an array of residences formatted in json
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void e_delete() {
        String url = baseURL + "delete/" + residence.getResidenceId();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        System.out.println("STATUS CODE: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());                               //Body should be true or false
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(true, Boolean.parseBoolean(response.getBody()));
    }
}