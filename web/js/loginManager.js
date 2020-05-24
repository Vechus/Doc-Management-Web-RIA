$(document).ready(function() {
    $("#loginbutton").on("click", function(e) {
        e.preventDefault();
        let form = document.getElementById("loginForm");
        if (form.checkValidity()) {
            let username = document.getElementById("username").value;
            let hashedPassword = CryptoJS.MD5(document.getElementById("password").value).toString();
            let formData = {username: username, passwordHash: hashedPassword};
            console.log("send formData");
            $.ajax({
                type: "POST",
                url: 'CheckLogin',
                data: JSON.stringify(formData),
                success: function(response) {
                    sessionStorage.setItem('username', response);
                    window.location.href = "home.html";
                },
                error: function(req) {
                    document.getElementById("errormessage").textContent = req.responseText;
                },
                dataType: "json"
            });
        } else {
            form.reportValidity();
        }
    })
})

