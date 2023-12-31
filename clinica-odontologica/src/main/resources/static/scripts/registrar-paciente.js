window.addEventListener('load', function () {
    const form = document.forms[0];
    const nombre = document.querySelector('#inputNombre');
    const apellido = document.querySelector('#inputApellido');
    const dni = document.querySelector('#inputDni');
    const fechaIngreso = this.document.querySelector('#inputFechaIngreso')
    const calle = this.document.querySelector('#inputCalle')
    const calleNumero = this.document.querySelector('#inputCalleNumero')
    const localidad = this.document.querySelector('#inputLocalidad')
    const provincia = this.document.querySelector('#inputProvincia')
    const url = 'http://localhost:8081';

    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const payload = {
            nombre: nombre.value, 
            apellido: apellido.value,
            dni: dni.value,
            fechaIngreso: fechaIngreso.value,
            domicilio: {
                calle: calle.value,
                numero: calleNumero.value,
                localidad: localidad.value,
                provincia: provincia.value
            }
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
        fetch(`${url}/pacientes/registrar`, settings)
            .then(response => {
                if (response.ok) {
                    return Promise.resolve({ mensaje: "Se ha registrado un nuevo Paciente."});
                } else if (response.status === 400) {
                    return response.json();
                } else {
                    return Promise.resolve({mensaje: 'No fue posible registrar el nuevo paciente'});
                }
            })
            .then(data => {
                let mensaje = '';
                for (const property in data) {
                    mensaje += `${data[property]}\n`;
                }
                alert(mensaje);                
            }).catch(err => {
                    alert('No fue posible registrar el nuevo paciente');
            })
    };


});
