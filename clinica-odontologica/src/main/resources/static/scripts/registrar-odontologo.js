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
                console.log(response);

                if (response.ok != true) {
                    alert("Alguno de los datos es incorrecto.");
                } else {
                    alert("Se ha registrado un nuevo Odontólogo.");
                }

                return response.json();

            })
            .then(data => {
                console.log("Promesa cumplida:");
                console.log(data);                
            }).catch(err => {
                console.log("Promesa rechazada:");
                console.log(err);
            })
    };


});
