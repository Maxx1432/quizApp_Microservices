package com.maxx.questionservice.controller;


import com.maxx.questionservice.model.Question;
import com.maxx.questionservice.model.QuestionWrapper;
import com.maxx.questionservice.model.Response;
import com.maxx.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService qusService;

    @Autowired
    Environment environment;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return qusService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return qusService.getQuestionByCategory(category);
    }

    @PostMapping(
            value = "add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> addQuestion(@RequestBody Question qus){
        return qusService.addQuestion(qus);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id)
    {
        return qusService.deleteQuestion(id);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question qus){
        return qusService.updateQuestion(qus);
    }
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName, @RequestParam Integer numQus){
        return  qusService.getQuestionsForQuiz(categoryName,numQus);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return qusService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return  qusService.getScore(responses);
    }
}

