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
        <%@include file="css/home.css"%>
    </style>
</head>

<body>
<header class="header">
    <a href="#" class="logo">KitchenMate</a>
    <nav class="nav-items">
        <a href="/untitled/home">Главная</a>
        <a href="/untitled/recipes">Рецепты</a>
        <a href="/untitled/profile">Личный кабинет</a>
    </nav>
</header>
<main>
    <div class="intro">
        <h1>KitchenMate</h1>
        <p>Интересные рецепты вы можете найти здесь</p>
        <button>Посмотреть рецепты</button>
    </div>
    <div class="achievements">
        <div class="work">
            <i class="fa fa-coffee" aria-hidden="true"></i>
            <p class="work-heading">Напитки</p>
            <p class="work-text">Здесь вы сможете найти много разнообразных рецептов напитков, как горячих, так и холодных, а также поделиться своими рецептами.</p>
        </div>
        <div class="work">
            <i class="fa fa-heart" aria-hidden="true"></i>
            <p class="work-heading">Здоровая еда</p>
            <p class="work-text">Множество людей объединились в одно сообщество, чтобы делиться своими рецептами здорового образа жизни и получать новый опыт.</p>
        </div>
        <div class="work">
            <i class="fa fa-certificate" aria-hidden="true"></i>
            <p class="work-heading">Русская кухня</p>
            <p class="work-text">Русская кухня очень многогранна и разнообразна. Она складывалась на протяжении многих веков, обогащалась культой других народов. </p>
        </div>
    </div>
    <div class="about-me">
        <div class="about-me-text">
            <h2>О приложении</h2>
            <p>KitchenMate - приложение для поиска рецептов. Оно было сделано для упрощения жизни пользователя, чтобы он не думал, что ему приготовить и мог найти это в подобных приложениях.</p>
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
</body>

</html>