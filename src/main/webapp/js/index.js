function showRegister() {
    document.getElementById("register").style.opacity = "1";
    document.getElementById('register').style.pointerEvents = 'auto';
    document.getElementById("login").style.opacity = "0.15";
    document.getElementById('login').style.pointerEvents = 'none';
    var x = document.getElementById("register");
    if (x.style.display === "none") {
        x.style.display = "block";
    }
}
function showLogin() {
    document.getElementById("login").style.opacity = "1";
    document.getElementById('login').style.pointerEvents = 'auto';
    document.getElementById("register").style.opacity = "0.15";
    document.getElementById('register').style.pointerEvents = 'none';
    var x = document.getElementById("login");
    if (x.style.display === "none") {
        x.style.display = "block";
    }
}
