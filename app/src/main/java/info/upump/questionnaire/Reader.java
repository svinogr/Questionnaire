package info.upump.questionnaire;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 23.09.2017.
 */

public class Reader {
    Activity activity;
    AssetManager am;

    public Reader(Activity activity) {
        this.activity = activity;
    }

    public List<Question> startReade() throws IOException {
        List<Question> list = new ArrayList<>();
         am = activity.getAssets();
        File file = new File("/assets/qu");

        String htmlString = readHtml(file);
        String[] arrayQuestions = getArrayQuestions(htmlString);

        for (int i = 0; i < 100; i++) {
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
                questionBody.setImg(attrImg);
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
                questionBody.setComment(Jsoup.parse(substring).text());
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

        System.out.println(list.size());
        for (Question q : list
                ) {
            System.out.println(q.toString());

        }
        return list;

    }

    private  String[] getArrayQuestions(String htmlString) {
        String[] split = htmlString.split("</i>\\)<br>");
        return split[1].split("<hr width=200px>");
    }

    private  String readHtml(File file) {
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
