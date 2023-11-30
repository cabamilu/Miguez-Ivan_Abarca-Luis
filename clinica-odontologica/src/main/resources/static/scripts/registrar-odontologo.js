window.addEventListener('load', function () {
    const form = document.forms[0];
    const nombre = document.querySelector('#inputNombre');
    const apellido = document.querySelector('#inputApellido');
    const numeroMatricula = document.querySelector('#inputNumeroMatricula');
    const url = 'http://localhost:8081';

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const payload = {
            numeroMatricula: numeroMatricula.value,
            nombre: nombre.value, 
            apellido: apellido.value
        };
        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json'
            }
        };
        realizarRegister(settings);

        form.reset();
    });

    function realizarRegister(settings) {
        fetch(`${url}/odontologos/registrar`, settings)
            .then(response => {
                if (response.ok) {
                    return Promise.resolve({ mensaje: "Se ha registrado un nuevo Odontólogo."});
                } else if (response.status === 400) {
                    return response.json();
                } else {
                    return Promise.resolve({mensaje: 'No fue posible registrar el nuevo odontólogo'});
                }
            })
            .then(data => {
                let mensaje = '';
                for (const property in data) {
                    mensaje += `${data[property]}\n`;
                }
                alert(mensaje);                
            }).catch(err => {
                alert('No fue posible registrar el nuevo odontólogo');
            })
    };


});
