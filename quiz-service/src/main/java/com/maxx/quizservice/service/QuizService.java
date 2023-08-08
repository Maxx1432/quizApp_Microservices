package com.maxx.quizservice.service;


import com.maxx.quizservice.dao.QuizDao;
import com.maxx.quizservice.feign.QuizInterface;
import com.maxx.quizservice.model.QuestionWrapper;
import com.maxx.quizservice.model.Quiz;
import com.maxx.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numOfQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numOfQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
//         List<Integer> questions =
//
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questions);
//        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionsIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionsIds);

//        List<Question> questionFromDB = quiz.get().getQuestions();
//       List<QuestionWrapper> questionForUser = new ArrayList<>();
//        for (Question q : questionFromDB)
//        {
//            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
//                    q.getOption2(), q.getOption3(), q.getOption4());
//            questionForUser.add(qw);
//        }

        return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        //        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestions();
//        Map<Integer, String> ansMap = new HashMap<>();
//        for(Question qus : questions)
//        {
//            ansMap.put(qus.getId(),qus.getRightAnswer());
//        }

//        int right =0;
//        for(Response response : responses){
//           if(response.getResponse().equals(ansMap.get(response.getId())))
//                right++;
//           /* if(response.getResponse().equals(questions.get(i).getRightAnswer()))
//                right++;
//
//            i++;*/
//        }
        return score;
    }
}
