package info.upump.questionnaire.entity;

/**
 * Created by explo on 23.09.2017.
 */

public class Answer {
    int id;
    String body;
    int right;

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +"\n"+
                ", body='" + body + '\'' +"\n"+
                ", right=" + right +"\n"+
                '}';
    }
}
