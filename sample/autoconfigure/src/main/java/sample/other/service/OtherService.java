package sample.other.service;

import org.springframework.stereotype.Service;

@Service(value = "sample.other.service.OtherService")
public class OtherService {

    public String show(String message) {
        return String.format("I can show %s", message);
    }

}
