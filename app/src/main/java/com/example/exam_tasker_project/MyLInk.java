package com.example.exam_tasker_project;

import java.net.URL;

public class MyLInk {

    public static int pos = 58;
    //тоже закономерность изменения сделаешь
    private String JSON_URL="https://raw.githubusercontent.com/Kitlad/exams/main/Task/subject/subject";

    public String getJSON_URL() {
        return JSON_URL;
    }

    public void setJSON_URL(String JSON_URL) {
        this.JSON_URL = JSON_URL;
    }
}
