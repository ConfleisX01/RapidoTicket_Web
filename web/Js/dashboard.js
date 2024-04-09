// Importamos todas las funciones del controlador de empleados
import * as ce from './empleados.js'
import * as cd from './destinos.js'
import * as cg from './general.js'

let idCamionSeleccionado;

function cargarMenu() {
  let btnVistaGeneral = document.getElementById('btnVistaGeneral')
  let btnPanelCamiones = document.getElementById('btnPanelCamiones')
  let btnPanelEmpleados = document.getElementById('btnPanelEmpleados')
  let btnPanelDestinos = document.getElementById('btnPanelDestinos')

  btnVistaGeneral.addEventListener("click", () => {
    cargarVistaGeneral()
  })

  btnPanelCamiones.addEventListener('click', () => {
    cargarPanelCamiones()
  })

  btnPanelEmpleados.addEventListener('click', () => {
    cargarPanelEmpleados()
  })

  btnPanelDestinos.addEventListener('click', () => {
    cargarPanelDestinos()
  })
}

function cargarControles() {
  let btnAgregarCamion = document.getElementById('btnAgregarCamion')
  let btnAsignar = document.getElementById('btnAsignar')
  let btnAgregarDestinos = document.getElementById('btnAgregarDestinos')

  btnAgregarCamion.addEventListener('click', () => {
    agregarCamion()
  })

  btnAsignar.addEventListener('click', () => {
    if (idCamionSeleccionado != null) {
      asignarConductor()
    }
  })

  btnAgregarDestinos.addEventListener('click', () => {
    if (idCamionSeleccionado != null) {
      agregarDestinos()
    }
  })
}

async function cargarVistaGeneral() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch("./general.html")
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
    cg.cargarContenido()
  } catch (error) {
    console.log(error)
  }
}

async function cargarPanelCamiones() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch('./buses.html')
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
    cargarControles()
    cargarTablaCamiones()
    cargarListaConductores()
    cargarListaDestinos()
  } catch (error) {
    console.log(error)
  }
}

async function cargarPanelEmpleados() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch('./employees.html')
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
    ce.cargarControles()
    ce.cargarTabla()
  } catch (error) {
    console.log(error)
  }
}

async function cargarPanelDestinos() {
  let contenedor = document.getElementById('main-container')

  try {
    const response = await fetch('./destinations.html')
    const html = await response.text()
    contenedor.innerHTML = ""
    contenedor.innerHTML = html
    cd.cargarControles()
    cd.cargarTabla()
  } catch (error) {
    console.log(error)
  }
}

async function agregarCamion() {
  fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarCamion')
    .then(response => {
      if (!response.ok) {
        throw new Error('Hubo un problema al llamar a la API.');
      }
      return response.json()
    })
    .then(async data => {
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: `${data.response}`,
        showConfirmButton: false,
        timer: 1500
      })
      fixSwal();
      document.getElementById('tblCamiones').innerHTML = "";
      cargarTablaCamiones();
      let ultimoId = await obtenerUltimoId();
      generarQr(ultimoId);
    })
    .catch(error => {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: `${error.message}`,
      });
      fixSwal();
    });
}

async function cargarTablaCamiones() {
  let table = document.getElementById('tblCamiones');
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')

  data.forEach(element => {
    let options = '';
    element.destinos.forEach(destino => {
      options += `<option>${destino}</option>`;
    });
    let content = `<tr data-id="${element.idCamion}">
                        <th scope="row">${element.idCamion}</th>
                        <th scope="row">${element.nombreConductor}</th>
                        <th scope="row">
                          <select class="form-select form-select-sm" aria-label="Small select example">
                            ${options}
                          </select>
                        </th>
                        <th scope="col"><span class="${element.estatus == 0 ? "badge text-bg-secondary" : "badge text-bg-primary"}">${element.estatus == 0 ? "Descansando" : "En Viaje"}</span></th>
                      </tr>`
    table.innerHTML += content
  })

  let filas = table.getElementsByTagName('tr');
  for (let i = 0; i < filas.length; i++) {
    filas[i].addEventListener('click', function () {
      cargarCamion(filas[i].getAttribute('data-id'));
    });
  }
}

async function cargarListaConductores() {
  let slcEmpleados = document.getElementById('slcConductor')
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAllEmpleados');

  data.forEach(element => {
    let option = document.createElement('option')
    option.text = element.persona.nombre
    option.value = element.idEmpleado
    slcEmpleados.add(option)
  })
}

async function cargarListaDestinos() {
  let slcDestinos = document.getElementById('slcDestinos')
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/destino/getAllDestinos')

  data.forEach(element => {
    let option = document.createElement('option')
    option.text = element.destinos
    option.value = element.idDestino
    slcDestinos.add(option)
  })
}

async function getAllData(URL) {
  try {
    const response = await fetch(URL)
    const json = await response.json()
    return json;
  } catch (error) {
    console.log(error)
  }
}

function fixSwal() {
  document.body.classList.remove('swal2-height-auto')
}

async function cargarCamion(index) {
  let qrContainer = document.getElementById('qrContainer')
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')
  let content = data[index - 1]
  let listDestinos = document.getElementById('listDestinos')
  listDestinos.innerHTML = ""

  idCamionSeleccionado = content.idCamion

  qrContainer.innerHTML = ""

  if (content && content.qr) {
    const img = document.createElement('img');
    img.src = 'data:image/png;base64,' + content.qr;
    qrContainer.appendChild(img)
  }

  if (content && content.destinos) {
    content.destinos.forEach(destino => {
      listDestinos.innerHTML += `<li class="list-group-item">${destino}</li>`;
    });
  }
}

async function asignarConductor() {
  const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/asignarConductor'
  let slcConductor = document.getElementById('slcConductor')
  let idConductor = slcConductor.value
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAllEmpleados')
  let idEmpleado

  data.forEach(element => {
    if (element.idEmpleado == idConductor) {
      idEmpleado = element.idEmpleado
    }
  });

  const formData = new URLSearchParams()
  formData.append('idEmpleado', idEmpleado)
  formData.append('idCamion', idCamionSeleccionado)

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData
  }

  try {
    const response = await fetch(URL, requestOptions)
    if (response.ok) {
      idCamionSeleccionado = null;
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Conductor asignado con exito",
        showConfirmButton: false,
        timer: 1500
      });
      fixSwal()
      document.getElementById('tblCamiones').innerHTML = ""
      cargarTablaCamiones()
    } else {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Error al asignar el conductor",
      });
      fixSwal()
    }
  } catch (error) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: `${error.message}`,
    });
    fixSwal()
  }
}

async function agregarDestinos() {
  const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarDestinos'
  let slcDestinos = document.getElementById('slcDestinos')
  let idDestino = slcDestinos.value
  let listDestinos = document.getElementById('listDestinos')
  const numeroDestinos = listDestinos.children.length

  const formData = new URLSearchParams()
  formData.append('idCamion', idCamionSeleccionado)
  formData.append('posicionDestino', numeroDestinos + 1)
  formData.append('idDestino', idDestino)

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData
  }

  try {
    const response = await fetch(URL, requestOptions)
    if (response.ok) {
      idCamionSeleccionado = null;
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Destino agregado con exito",
        showConfirmButton: false,
        timer: 1500
      });
      fixSwal()
      cargarPanelCamiones()
    } else {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Error al agregar el destino",
      });
      fixSwal()
    }
  } catch (error) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: `${error.message}`,
    });
    fixSwal()
  }
}

async function generarQr(idCamion) {
  const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarQr'
  let response = await fetch(`https://quickchart.io/qr?text=${idCamion}&format=base64&size=200&margin=1`)
  let base64 = await response.text()


  const formData = new URLSearchParams()
  formData.append('idCamion', idCamion)
  formData.append('qr', base64)

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: formData
  }

  try {
    const response = await fetch(URL, requestOptions)
  } catch (error) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: `${error.message}`,
    });
    fixSwal()
  }
}

async function obtenerUltimoId() {
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')
  let largo = data.length;

  return data[largo - 1].idCamion
}

cargarMenu()
cargarVistaGeneral()