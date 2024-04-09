export function cargarContenido() {
    cargarDestinosTotales()
    cargarCamionesTotales()
    cargarEmpleadosTotales()
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