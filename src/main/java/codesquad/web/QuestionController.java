package codesquad.web;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String printContents(@PathVariable int index, Model model) {
        model.addAttribute("question", questions.get(index-1));
        return "/qna/show";
    }
}
