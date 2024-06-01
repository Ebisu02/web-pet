<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KitchenMate</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"></link>
    <style>
        <%@include file="css/profile.css"%>
    </style>
</head>

<body>
<header class="header">
  <a href="home" class="logo">KitchenMate</a>
  <nav class="nav-items">
    <a href="home">Главная</a>
    <a href="recipes">Рецепты</a>
    <a href="profile">Личный кабинет</a>
  </nav>
</header>
<main>
  <div class="login">
    <header class="login__header">
      <h2>
        <svg class="icon">
          <use xlink:href="#icon-lock" />
        </svg>
        KitchenMate
      </h2>
    </header>
    <div class="login__form">
      <div>
        <label class="uName">Имя пользователя: {uName}</label>
      </div>
      <div>
        <label class="uMail">Почта: {uMail}</label>
      </div>
      <div>
        <label class="uAboutLabel">О себе: </br>
          <div class="aboutContainer">
            <textarea name="about" class="uAbout" cols="45" rows="10">{uAbout}</textarea>
            <button class="submitAbout">Подтвердить</button>
          </div>
        </label>
      </div>
      <div>
        </br>
        <input class="changePwd" type="button" value="Сменить пароль">
        </br>
        <input class="signOut" type="button" value="Выйти из аккаунта">
      </div>
    </div>
  </div>
</main>
<footer class="footer">
  <div class="copy">&copy; 2024 Ososov S.A.</div>
  <div class="bottom-links">
    <div class="links">
      <span>Контакты</span>
      <a href="#">helloimclinker@gmail.com</a>
    </div>
  </div>
</footer>
<svg xmlns="http://www.w3.org/2000/svg" class="icons">
  <symbol id="icon-lock" viewBox="0 0 24 24" fill="none">
    <path d="M14 6H10M15 17C14.7164 15.8589 13.481 15 12 15C10.519 15 9.28364 15.8589 9 17M12 11H12.01M8.2 21H15.8C16.9201 21 17.4802 21 17.908 20.782C18.2843 20.5903 18.5903 20.2843 18.782 19.908C19 19.4802 19 18.9201 19 17.8V6.2C19 5.0799 19 4.51984 18.782 4.09202C18.5903 3.71569 18.2843 3.40973 17.908 3.21799C17.4802 3 16.9201 3 15.8 3H8.2C7.0799 3 6.51984 3 6.09202 3.21799C5.71569 3.40973 5.40973 3.71569 5.21799 4.09202C5 4.51984 5 5.07989 5 6.2V17.8C5 18.9201 5 19.4802 5.21799 19.908C5.40973 20.2843 5.71569 20.5903 6.09202 20.782C6.51984 21 7.07989 21 8.2 21ZM13 11C13 11.5523 12.5523 12 12 12C11.4477 12 11 11.5523 11 11C11 10.4477 11.4477 10 12 10C12.5523 10 13 10.4477 13 11Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
  </symbol>
</svg>
</body>

</html>