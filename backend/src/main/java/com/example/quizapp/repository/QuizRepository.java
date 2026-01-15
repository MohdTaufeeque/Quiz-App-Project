package com.example.quizapp.repository;

import com.example.quizapp.model.QuizQuestion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<QuizQuestion> mapper = (rs, rowNum) -> {
        QuizQuestion q = new QuizQuestion();
        q.setId(rs.getInt("id"));
        q.setQuestion(rs.getString("question"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        return q;
    };

    public List<QuizQuestion> findAll() {
        return jdbcTemplate.query("SELECT * FROM quiz_question", mapper);
    }

    public Optional<QuizQuestion> findById(int id) {
        var list = jdbcTemplate.query("SELECT * FROM quiz_question WHERE id = ?", mapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public void save(QuizQuestion q) {
        jdbcTemplate.update(
                "INSERT INTO quiz_question (question, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)",
                q.getQuestion(), q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD(), q.getCorrectOption()
        );
    }
}
