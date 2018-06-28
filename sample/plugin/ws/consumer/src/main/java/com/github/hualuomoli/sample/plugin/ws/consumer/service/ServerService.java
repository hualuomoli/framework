package com.github.hualuomoli.sample.plugin.ws.consumer.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.github.hualuomoli.sample.plugin.ws.api.entity.User;
import com.github.hualuomoli.sample.plugin.ws.consumer.entity.Data;

@WebService(targetNamespace = "http://sample.hualuomoli.github.com")
public interface ServerService {

  String execute(@WebParam(name = "params") String param);

  String inObject(@WebParam(name = "user") User user);

  String inArray(@WebParam(name = "users") List<User> users);

  User outObject(@WebParam(name = "params") String param);

  List<User> outArray(@WebParam(name = "params") String param);

  Data my(@WebParam(name = "user") Data data);

}
