package com.example.qr_code_generator.Models;

public class TextModels {
    public TextModels() {
    }

    String TextInput;

    public String getTextInput() {
        return TextInput;
    }

    public void setTextInput(String textInput) {
        TextInput = textInput;
    }

    @Override
    public String toString() {
        return "TextModels{" +
                "TextInput='" + TextInput + '\'' +
                '}';
    }
}
