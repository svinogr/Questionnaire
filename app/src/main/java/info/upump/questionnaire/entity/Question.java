package info.upump.questionnaire.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by explo on 23.09.2017.
 */

public class Question {
    int id;
    int nunber;
    String body;
    String img;
    List<Answer> answers = new ArrayList();
    String comment;
    String category;

    public Question() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNunber() {
        return this.nunber;
    }

    public void setNunber(int nunber) {
        this.nunber = nunber;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return "Question{\nid=" + this.id + "\n, nunber=" + this.nunber + "\n, body='" + this.body + '\'' + "\n, img='" + this.img + '\'' + "\n, answers=" + this.answers + "\n, comment='" + this.comment + '\'' + ", category='" + this.category + '\'' + '}';
    }
}
