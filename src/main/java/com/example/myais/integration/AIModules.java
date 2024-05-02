package com.example.myais.integration;

public enum AIModules {

    KIMI("C:\\Users\\吴松林\\PycharmProjects\\pythonProject\\src\\moon_shot_script\\test1.py"),
    TONGYI("C:\\Users\\吴松林\\PycharmProjects\\pythonProject\\src\\tongyi-script\\test1.py"),
    GPT("C:\\Users\\吴松林\\PycharmProjects\\pythonProject\\src\\chatGPT_script\\test1.py");

    final String path;

    AIModules(String path) {
        this.path = path;
    }
}
