let idCamionSeleccionado;

function cargarMenu() {
  let btnVistaGeneral = document.getElementById('btnVistaGeneral')
  let btnPanelCamiones = document.getElementById('btnPanelCamiones')

  btnVistaGeneral.addEventListener("click", () => {
    cargarVistaGeneral()
  })

  btnPanelCamiones.addEventListener('click', () => {
    cargarPanelCamiones()
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

function agregarCamion() {
  fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/agregarCamion')
    .then(response => {
      if (!response.ok) {
        throw new Error('Hubo un problema al llamar a la API.');
      }
      return response.json()
    })
    .then(data => {
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: `${data.response}`,
        showConfirmButton: false,
        timer: 1500
      });
      fixSwal();
      document.getElementById('tblCamiones').innerHTML = ""
      cargarTablaCamiones()
    })
    .catch(error => {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: `${error.message}`,
      });
      fixSwal()
    });
}

async function cargarTablaCamiones() {
  let table = document.getElementById('tblCamiones');
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')

  let index = 0;

  data.forEach(element => {
    let options = '';
    index = index + 1;
    element.destinos.forEach(destino => {
      options += `<option>${destino}</option>`;
    });
    let content = `<tr onclick = "cargarCamion(${index})">
                        <th scope="row">${element.idCamion}</th>
                        <th scope="row">${element.nombreConductor}</th>
                        <th scope="row">
                          <select class="form-select form-select-sm" aria-label="Small select example">
                            ${options}
                          </select>
                        </th>
                        <th scope="row">
                          ${element.estatus = 1 ? '<span class = "badge text-bg-primary">Descansando</span>' : '<span class = "badge text-bg-success">En Viaje</span>'}
                        </th>
                      </tr>`
    table.innerHTML += content
  })
}

async function cargarListaConductores() {
  let slcEmpleados = document.getElementById('slcConductor')
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAll');

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
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')
  let content = data[index - 1]
  let listDestinos = document.getElementById('listDestinos')
  listDestinos.innerHTML = ""

  generarQr(content.idCamion)

  idCamionSeleccionado = content.idCamion

  if (content && content.destinos) {
    content.destinos.forEach(destino => {
      listDestinos.innerHTML += `<li class="list-group-item">${destino}</li>`
    })
  }
}

async function asignarConductor() {
  const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/asignarConductor'
  let slcConductor = document.getElementById('slcConductor')
  let idConductor = slcConductor.value
  let data = await getAllData('http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAll')
  let nombreConductor = ''

  data.forEach(element => {
    if (element.idEmpleado == idConductor) {
      nombreConductor = element.persona.nombre
    }
  });

  const formData = new URLSearchParams()
  formData.append('nombreConductor', nombreConductor)
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
  let qrContainer = document.getElementById('qrContainer')
  let btnGenerar = document.getElementById('btnGenerarQr')
  let response = await fetch(`https://quickchart.io/qr?text=${idCamion}&format=base64&size=200&margin=1`)
  let base64 = await response.text()

  const img = document.createElement('img')
  img.src = 'data:image/png;base64,' + base64

  qrContainer.innerHTML = '';
  qrContainer.appendChild(img)

  btnGenerar.addEventListener('click', () => {

  })
}

cargarMenu()
cargarVistaGeneral()