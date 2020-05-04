function checkPassword() {
    let password, confirm;
    password = document.getElementById('password').value;
    confirm = document.getElementById('confirm').value;
    if (password === confirm){
        document.getElementById('regBtn').removeAttribute('disabled');
        return;
    }
    document.getElementById('regBtn').setAttribute('disabled', 'disabled');
}
