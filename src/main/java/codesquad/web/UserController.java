package codesquad.web;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", findUser(userId));
        return "/user/profile";
    }

    private User findUser(@PathVariable String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model){
        model.addAttribute("user", findUser(userId));
        return "/user/update_form";
    }

    @PostMapping("/users/{userId}/update")
    public String modifyUser(@PathVariable String userId, User user, Model model, HttpServletResponse response){
        if(user.getPassword().equals(findUser(userId).getPassword())){
            int userIndex = users.indexOf(findUser(userId));
            users.remove(userIndex);
            users.add(userIndex, user);
            return "redirect:/users";
        }
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('비밀번호를 확인해주세요.'); history.go(-1);</script>");
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
