<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html lang="en">
<style>
    <%@include file="css/login.css"%>
</style>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
    <title>SignUp/KitchenMate</title>
</head>
<body class="align">
<div class="login">
    <div>
        <header class="login__header">
            <h2>
                <svg class="icon">
                    <use xlink:href="#icon-lock" />
                </svg>
                KitchenMate
            </h2>
        </header>
        <form action="#" class="login__form" method="POST">
            <div>
                <label for="uname">Имя пользователя</label>
                <input type="text" id="uname" name="uname" placeholder="Имя пользователя"></input>
            </div>
            <div>
                <label for="pwd">Пароль</label>
                <input type="password" id="pwd" name="pwd" placeholder="Пароль"></input>
            </div>
            <div>
                <label for="pwdСheck">Подтверждение пароля</label>
                <input type="password" id="pwdСheck" name="pwdСheck" placeholder="Подтвердите пароль"></input>
            </div>
            <div>
                <label for="email">Почта</label>
                <input type="email" id="email" name="email" placeholder="Введите e-mail"></input>
            </div>
            <div>
                <label for="about">О себе</label>
                <textarea rows="4" class="bigInput" name="about" id="about" cols="25" placeholder="Расскажите немного о себе"></textarea>
            </div>
            <div>
                <input class="button" type="submit" value="Зарегистрироваться"></input>
            </div>
        </form>
    </div>
</div>
<svg xmlns="http://www.w3.org/2000/svg" class="icons">
    <symbol id="icon-lock" viewBox="0 0 448 512">
        <path d="M400 224h-24v-72C376 68.2 307.8 0 224 0S72 68.2 72 152v72H48c-26.5 0-48 21.5-48 48v192c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48V272c0-26.5-21.5-48-48-48zm-104 0H152v-72c0-39.7 32.3-72 72-72s72 32.3 72 72v72z" />
    </symbol>
</svg>
</body>