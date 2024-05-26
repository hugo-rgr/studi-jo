
var countDownDate = new Date("Jul 26, 2024 19:30:00").getTime();

var countdownfunction = setInterval(function() {
    
    var now = new Date().getTime();
    
    var interval = countDownDate - now;

    var days = Math.floor(interval / (1000 * 60 * 60 * 24));
    var hours = Math.floor((interval % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((interval % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((interval % (1000 * 60)) / 1000);

    document.getElementById("countdown").innerHTML = days + " jours " + hours + " heures "
        + minutes + " minutes " + seconds + " secondes ";

    if (interval < 0) {
        clearInterval(countdownfunction);
        document.getElementById("countdown").innerHTML = "Les Jeux Olympiques ont commencé!";
    }
}, 1000); // Update the countdown every second
