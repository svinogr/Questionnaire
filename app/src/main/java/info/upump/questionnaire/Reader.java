package info.upump.questionnaire;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.AssetManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.db.AnswerDAO;
import info.upump.questionnaire.db.QuestionDAO;
import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 23.09.2017.
 */

public class Reader {
    Activity activity;
    AssetManager am;
    private static String alphaRu = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");
    private  static  String[] alphaEn = {"a","b","v","g","d","e","yo","g","z","i","y","i",
            "k","l","m","n","o","p","r","s","t","u",
            "f","h","tz","ch","sh","sh","'","e","yu","ya"};
    public Reader(Activity activity) {
        this.activity = activity;
    }

    public void startReader() throws IOException {
        List<Question> list = new ArrayList<>();
        am = activity.getAssets();

        String htmlString = readHtml();
        String[] arrayQuestions = getArrayQuestions(htmlString);

        for (int i = 0; i < arrayQuestions.length - 1; i++) {
            ContentValues contentValues = new ContentValues();
            Question questionBody = new Question();
            Document parse = Jsoup.parse(arrayQuestions[i], "windows-1251");
            int startQuestion = parse.body().html().indexOf("Вопрос:") + 8;
            int startKategorii = parse.body().html().indexOf("Категории:");
            int startType = parse.body().html().indexOf("Тип вопроса:");
            int startAnswer = parse.body().html().indexOf("Ответы:");
            int startImg = parse.body().html().indexOf("<center>");
            int startKomment = parse.body().html().indexOf("Комментарий:");
            int end = parse.body().html().length();

            String question = Jsoup.parse(parse.body().html().substring(startQuestion, startKategorii)).text();

            questionBody.setBody(question);
            //высчитвываем конец для ответов если рисунка нет

            if (startImg < 0) {
                if (startKomment > 0) {
                    startImg = startKomment - 3;

                } else startImg = end;
            }

            //если рисунок есть

            if (startImg > 0) {
                String img = parse.select("center").html();
                String attrImg = Jsoup.parse(img).select("img").attr("src");
                String s = renameImg(attrImg);
                questionBody.setImg(s);
            }

            //получаем спсиок ответов

            String answer = parse.body().html().substring(startAnswer, startImg);

            String[] brs = answer.split("<br>");
            for (int b = 1; b < brs.length; b++) {// b=1 тк убираем слово ответ

                if (!brs[b].trim().equals("")) {
                    Answer answerBody = new Answer();
                    answerBody.setBody(Jsoup.parse(brs[b]).text());
                    if (brs[b].contains("<i>")) {
                        //           System.out.println(Jsoup.parse(b + " " + brs[b]).text() + "- почти правильный");
                        answerBody.setRight(0);
                    } else if (brs[b].contains("<u>")) {
                        //            System.out.println(Jsoup.parse(b + " " + brs[b]).text() + "- правильный");
                        answerBody.setRight(1);
                    } else {
                        //             System.out.println(b + " " + brs[b]);
                        answerBody.setRight(-1);
                    }
                    questionBody.getAnswers().add(answerBody);
                }

            }

            //коментарий
            if (startKomment > 0) {
                String substring = parse.body().html().substring(startKomment + 12, end);
                String com = Jsoup.parse(substring).text();
                questionBody.setComment(com);
            }

            //категории
            if (startKategorii > 0) {
                String substring;
                if (startType < 0) {
                    substring = parse.body().html().substring(startKategorii, startAnswer);
                } else {
                    substring = parse.body().html().substring(startKategorii, startType);
                }
                questionBody.setCategory(Jsoup.parse(substring).text());
            }
            list.add(questionBody);

        }
      /*  AnswerDAO answeDAO = new AnswerDAO(activity.getApplicationContext());
        Cursor answerByQuation = answeDAO.getAnswerByQuation(3901);
       answerByQuation.moveToFirst();
        do {
           System.out.println(answerByQuation.getString(1));
       }
        while (answerByQuation.moveToNext());
*/

        QuestionDAO questionDAO = new QuestionDAO(activity.getApplicationContext());
        AnswerDAO answeDAO = new AnswerDAO(activity.getApplicationContext());
        for (Question question : list) {
            int id = (int) questionDAO.save(question);
            question.setId(id);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
                answeDAO.save(answer);
            }
        }
/*
        System.out.println(list.size());
        for (Question q : list
                ) {
            System.out.println(q.toString());

        }*/


    }

    private String renameImg(String attrImg) {
        if (attrImg.equals("")) {
            return null;
        }
        if (attrImg.contains("html")) {
            return null;
        }

        if (attrImg.contains("htm")) {
            return null;
        }
        String substring = attrImg.replaceAll("-", "_").substring(6, attrImg.length() - 4);
        String low = substring.toLowerCase();

        char[] chars =low.toCharArray();
        StringBuilder bufferedReader= new StringBuilder();
        for (int j =0; j<chars.length;j++){

            int c = alphaRu.indexOf(chars[j]);
            if(c !=-1){
                bufferedReader.append(alphaEn[c]);
            }else bufferedReader.append(chars[j]);

        }
        if(bufferedReader.toString().contains(".gif")){
            bufferedReader.append("g");
        } else bufferedReader.append("j");

        return "img" + bufferedReader;


    }

    private String[] getArrayQuestions(String htmlString) {
        String[] split = htmlString.split("</i>\\)<br>");
        return split[1].split("<hr width=200px>");
    }

    private String readHtml() {
        String s;
        InputStreamReader scanner = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            scanner = new InputStreamReader(am.open("question.html"), "windows-1251");
            bufferedReader = new BufferedReader(scanner);

            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);

            }
            scanner.close();
            bufferedReader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }
}
