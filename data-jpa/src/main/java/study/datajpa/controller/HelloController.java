package study.datajpa.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.service.HelloService;

@AllArgsConstructor
@RestController
public class HelloController {

    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        helloService.hello();
        return "hello";
    }
}
