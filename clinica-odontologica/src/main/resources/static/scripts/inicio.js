window.addEventListener('load', function () {
    const buttonOdontologo = document.querySelector('#registrar-odontologo-button');
    const buttonPaciente = document.querySelector('#registrar-paciente-button');

    buttonOdontologo.addEventListener('click', function (event) {
        location.href = '/registrar-odontologo.html';
    });
    buttonPaciente.addEventListener('click', function (event) {
        location.href = '/registrar-paciente.html';
    });
});
