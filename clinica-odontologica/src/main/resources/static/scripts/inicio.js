window.addEventListener('load', function () {
    const buttonOdontologo = document.querySelector('#registrar-odontologo-button');
    const buttonPaciente = document.querySelector('#registrar-paciente-button');
    const buttonTurno = document.querySelector('#registrar-turno-button');

    buttonOdontologo.addEventListener('click', function (event) {
        location.href = '/registrar-odontologo.html';
    });
    buttonPaciente.addEventListener('click', function (event) {
        location.href = '/registrar-paciente.html';
    });
    buttonTurno.addEventListener('click', function (event) {
        location.href = '/registrar-turno.html';
    });
});
