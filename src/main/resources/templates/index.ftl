<!DOCTYPE html>
<html lang="ru">
<head>

    <meta charset="utf-8">

    <title>UniService</title>
    <meta name="description" content="">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- Chrome, Firefox OS and Opera -->
    <meta name="theme-color" content="#fff">
    <!-- Windows Phone -->
    <meta name="msapplication-navbutton-color" content="#fff">
    <!-- iOS Safari -->
    <meta name="apple-mobile-web-app-status-bar-style" content="#fff">

    <link rel="stylesheet" href="css/main.min.css">
</head>

<body>
<div class="content">

    <header>
        <nav class="header-container">
            <a class="logo">UniService</a>
        </nav>
    </header>

    <div class="container">
        <div class="panel col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-8
         col-xs-offset-2 centered">
            <div class="welcome">
                <h1 class="h1">Добро пожаловать на UniService!</h1>
                <p>Навигация по внеучебной жизни университета</p>
            </div>

            <#if model.error??>
                <div class = "alert">
                    Неправильно введён логин/пароль. Попробуйте ещё раз. Если ввод верен - свяжитесь с UniServiceTeam
                </div>
            </#if>
            <form name='form-login'  action="/login" method="POST">
                <div class="input-box">
                    <input type="text" name="login" placeholder="Логин" autofocus required>
                    <span><i class="far fa-user-circle"></i></span>
                </div>
                <div class="input-box">
                    <input type="password" name="password" placeholder="Пароль" required>
                    <span><i class="fas fa-lock"></i></span>
                </div>
                <div class="input-box">
                    <label class="remember-me"><input name="" value="remember" type="checkbox" /> &nbsp;Запомнить меня</label>
                </div>
                <button type="submit" value="Войти">Войти</button>
            </form>
        </div>
    </div>
</div>

<footer>
    <div class="footer-container">
        <div class="footer-text">2018, UniService Team
            <div class="social-media">
                <a href="https://vk.com/itis_kpfu"><i class="fab fa-vk fa-lg"></i></a>
            </div>
        </div>
    </div>
</footer>

<script src="js/scripts.min.js"></script>

</body>
</html>