export function cargarContenido() {
    cargarDestinosTotales()
    cargarCamionesTotales()
    cargarEmpleadosTotales()
    cargarMensajes()
    cargarMensajesTotales()
    cargarUsuariosTotales()
    cargarGrafica()
}

async function cargarCamionesTotales() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones'
    let data = await getAllData(URL)
    let texto = document.getElementById('lbTotalCamiones')

    let cantidadCamiones = data.length

    texto.innerHTML = ""
    texto.innerHTML = cantidadCamiones
}

async function cargarDestinosTotales() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/destino/getAllDestinos'
    let data = await getAllData(URL)
    let texto = document.getElementById('lbTotalDestinos')

    let cantidadDestinos = data.length

    texto.innerHTML = ""
    texto.innerHTML = cantidadDestinos
}

async function cargarEmpleadosTotales() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAllEmpleados'
    let data = await getAllData(URL)
    let texto = document.getElementById('lbTotalEmpleados')

    let cantidadEmpleados = data.length

    texto.innerHTML = ""
    texto.innerHTML = cantidadEmpleados
}

async function cargarMensajes() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/mensajes/getAllMensajes'
    let data = await getAllData(URL)
    let container = document.getElementById('messages-container')

    console.log(data)

    data.forEach(element => {
        let content = `<div class="container-fluid message-card d-flex">
        <div class="separator d-none d-md-block">
            <div class="dot"></div>
        </div>
        <div class="mx-2 p-2 d-flex align-items-center justify-content-center d-none d-md-block">
            <img src="${element.foto}" alt="Empleoyee" class="msg-img">
        </div>
        <div class="flex-grow-1 message-content m-2 p-2">
            <p class="m-0">${element.mensaje}</p>
        </div>
    </div>`

    container.innerHTML += content

    });
}

async function cargarMensajesTotales() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/mensajes/getAllMensajes'
    let data = await getAllData(URL)
    let lbTotalMensajes = document.getElementById('lbTotalMensajes')

    lbTotalMensajes.innerHTML = data.length
}

async function cargarUsuariosTotales() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/usuario/getAllUsuarios'
    let data = await getAllData(URL)
    let lbTotalUsuarios = document.getElementById('lbTotalUsuarios')

    lbTotalUsuarios.innerHTML = data.length
}

async function cargarGrafica() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones'
    let data = await getAllData(URL)
    let grafica = document.getElementById('circular-grafic')
    let texto = document.getElementById('lbPorcentaje')

    let cantidadCamiones = data.length
    let camionesActivos = data.filter(camion => camion.estatus == 1).length

    grafica.style.setProperty('--total-camiones', cantidadCamiones)
    grafica.style.setProperty('--active-camiones', camionesActivos)

    let porcentaje = (camionesActivos / cantidadCamiones) * 100

    texto.innerText = `${porcentaje.toFixed(0)}%`
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