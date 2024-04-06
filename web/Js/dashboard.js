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
  let btnAgregarCamion = document.getElementById('btnAgregarCamion');

  btnAgregarCamion.addEventListener('click', () => {
    agregarCamion()
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
    let content = await example()
    console.log(content)
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

function cargarTablaCamiones() {
  let table = document.getElementById('tblCamiones');
  fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')
    .then(response => response.json())
    .then(data => {
      data.forEach(element => {
        let options = '';
        element.destinos.forEach(destino => {
          options += `<option>${destino}</option>`;
        });
        let content = `<tr>
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
                      </tr>`;
        table.innerHTML += content;
      });
    });
}

async function example() {
  try {
    const response = await fetch('http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones')
    const json = await response.json()
    return json;
  } catch(error) {
    console.log(error)
  }
}

function fixSwal() {
  document.body.classList.remove('swal2-height-auto');
}

cargarMenu()
cargarVistaGeneral()