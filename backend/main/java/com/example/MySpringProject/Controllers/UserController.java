package com.example.MySpringProject.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.MySpringProject.Config.*;
import com.example.MySpringProject.DTO.*;
import com.example.MySpringProject.DAO.Repositories.*;
import com.example.MySpringProject.Service.Impl.JwtUserDetailsService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;

    @PostMapping("/signup")
    public Response signup(@RequestBody UserDTO infoDto) { // 회원 추가
        Response response = new Response();

        try {
            userService.save(infoDto);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

}