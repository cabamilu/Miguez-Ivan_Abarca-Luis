window.addEventListener('load', function () {
  const urlPacientes = 'http://localhost:8081/pacientes';
  const urlOdontologos = 'http://localhost:8081/odontologos';
  const url = 'http://localhost:8081';
  let listaPacientes = [];
  let listaOdontologos = [];
  let pacienteId, odontologoId;
  
  obtenerPacientes();
 
  function obtenerPacientes() {
    const settings = {
      method: 'GET',
    };
    fetch(urlPacientes, settings)
      .then(response => response.json())
      .then(pacientes => {
        listaPacientes = pacientes;
        renderizarPacientes()
      })
      .catch(error => console.log(error));
  };

  function renderizarPacientes() {
    const titulo = document.querySelector('.form-header h2');
    titulo.textContent = listaPacientes.length === 0 ? 'No hay pacientes registrados' : 'Seleccione Paciente';
    const listadoPacientes = document.querySelector('#listado');
    listadoPacientes.innerHTML = "";

    listaPacientes.forEach(paciente => {
      listadoPacientes.innerHTML += `
        <li class="tarea">
          <div class="descripcion">
            <p class="nombre">${paciente.nombre}</p>
            <p class="nombre">${paciente.apellido}</p>
          </div>
          <button class="change" id="${paciente.id}"><i class="fa-regular fa-circle-check"></i></button>
        </li>
        `
    });

    const liPacientes= document.querySelectorAll('.change');
    liPacientes.forEach(button => {
      button.addEventListener('click', function(event) {
        const {id} = event.target;
        pacienteId = id;
        obtenerOdontologos();
      })
    })
  }

  function obtenerOdontologos() {
    const settings = {
      method: 'GET',
    };
    fetch(urlOdontologos, settings)
      .then(response => response.json())
      .then(odontologos => {
        listaOdontologos = odontologos;
        renderizarOdontologos();
      })
      .catch(error => console.log(error));
  };

  function renderizarOdontologos() {
    const titulo = document.querySelector('.form-header h2');
    titulo.textContent = listaOdontologos.length === 0 ? 'No hay odontólogos registrados' : 'Seleccione Odontólogo';
    const listadoOdontologos = document.querySelector('#listado');
    listadoOdontologos.innerHTML = "";

    listaOdontologos.forEach(odontologo => {
      listadoOdontologos.innerHTML += `
        <li class="tarea">
          <div class="descripcion">
            <p class="nombre">${odontologo.nombre}</p>
            <p class="nombre">${odontologo.apellido}</p>
          </div>
          <button class="change" id="${odontologo.id}"><i class="fa-regular fa-circle-check"></i></button>
        </li>
        `
    });

    const liOdontologos= document.querySelectorAll('.change');
    liOdontologos.forEach(button => {
      button.addEventListener('click', function(event) {
        const {id} = event.target;
        odontologoId = id;
        seleccionarFechaYHora();
      })
    })
  }

  function seleccionarFechaYHora() {
    const container = document.querySelector('.container');
    const listado = document.querySelector('#listado');
    listado.remove();
    const titulo = document.querySelector('.form-header');
    titulo.remove();
    const form = document.createElement('form');
    const label = document.createElement('label');
    label.innerHTML = 'Seleccione Fecha y Hora';
    const inputFecha = document.createElement('input');
    inputFecha.id = 'inputFecha';
    inputFecha.type = 'date';
    const inputHora = document.createElement('input');
    inputHora.id = 'inputHora';
    inputHora.type = 'time';
    const button = document.createElement('button');
    button.innerHTML = "Registrar Turno";
    button.type = 'submit';
    form.addEventListener('submit', function(event) {
      event.preventDefault();
      registrarTurno();
    });

    form.appendChild(label);
    form.appendChild(inputFecha);
    form.appendChild(inputHora);
    form.appendChild(button);
    container.appendChild(form);
  }
  
  function registrarTurno() {
    const form = document.forms[0];
    const fecha = document.querySelector('#inputFecha');
    const hora = document.querySelector('#inputHora');
    const payload = {
      fechaYHora: `${fecha.value}T${hora.value}`,
      odontologoId: odontologoId, 
      pacienteId: pacienteId
    };
    console.log(payload);
    const settings = {
        method: 'POST',
        body: JSON.stringify(payload),
        headers: {
            'Content-Type': 'application/json'
        }
    };
    realizarRegister(settings);
    form.reset();
  }

  function realizarRegister(settings) {
    fetch(`${url}/turnos/registrar`, settings)
      .then(response => {
        if (response.ok) {
            return Promise.resolve('Se ha registrado un nuevo Turno.');
        } else if (response.status === 400) {
            return Promise.resolve('La fecha y hora deben ser posterior a las actuales');
        } else {
            return Promise.resolve('No fue posible registrar el nuevo turno');
        }
      })
      .then(mensaje => {
          alert(mensaje);                
      }).catch(err => {
          alert('No fue posible registrar el nuevo turno');
      }).finally(() => setTimeout(() => (location.href = '/'), 200));
};
});
