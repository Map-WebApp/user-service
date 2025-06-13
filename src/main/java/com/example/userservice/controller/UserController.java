
package com.example.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
