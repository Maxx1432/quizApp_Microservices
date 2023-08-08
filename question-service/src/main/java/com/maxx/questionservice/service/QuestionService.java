package com.maxx.questionservice.service;

import com.maxx.questionservice.dao.QuestionDao;
import com.maxx.questionservice.model.Question;
import com.maxx.questionservice.model.QuestionWrapper;
import com.maxx.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    QuestionDao qusDao;
    public ResponseEntity <List<Question>> getAllQuestions() {
       try{
           return new ResponseEntity<>(qusDao.findAll(), HttpStatus.OK);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public  ResponseEntity<List<Question>> getQuestionByCategory(String category){
        try {
            return new ResponseEntity<>(qusDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> addQuestion(Question qus) {
        try {
            qusDao.save(qus);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            qusDao.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question qus) {
        try {
            qusDao.saveAndFlush(qus);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQus) {

        List<Integer> questions = qusDao.findRandomQuestionsByCategory(categoryName,numQus);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        questionIds.forEach((id)->questions.add(qusDao.findById(id).get()));

        questions.forEach((question) -> {
            wrappers.add(new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            ));
        });

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right =0;
        for(Response response : responses){
            Question qus = qusDao.findById(response.getId()).get();
            if(response.getResponse().equals(qus.getRightAnswer()))
                right++;
           /* if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;*/
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
